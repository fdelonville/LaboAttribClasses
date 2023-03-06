package be.technobel.materialloc.service.impl;

import be.technobel.materialloc.exceptions.NotFoundException;
import be.technobel.materialloc.exceptions.RequestStatusException;
import be.technobel.materialloc.models.dto.ReducedRequestDTO;
import be.technobel.materialloc.models.dto.RequestDTO;
import be.technobel.materialloc.models.entity.Request;
import be.technobel.materialloc.models.entity.RequestStatus;
import be.technobel.materialloc.models.entity.Room;
import be.technobel.materialloc.models.entity.users.Person;
import be.technobel.materialloc.models.form.RequestForm;
import be.technobel.materialloc.repository.*;
import be.technobel.materialloc.service.RequestService;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final MaterialRepository materialRepository;
    private final PersonRepository personRepository;

    private final RoomRepository roomRepository;

    public RequestServiceImpl(RequestRepository requestRepository, MaterialRepository materialRepository, PersonRepository personRepository, RoomRepository roomRepository) {
        this.requestRepository = requestRepository;
        this.materialRepository = materialRepository;
        this.personRepository = personRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void create(RequestForm form) {

        Request request = form.toEntity();

        // link user
        Person p = personRepository.findByLogin(form.getUserLogin())
                .orElseThrow(); // TODO: préciser

        if( Objects.equals(p.getRole(), "ADMIN") )
            throw new RuntimeException(); // TODO: préciser

        request.setMadeBy( p );

        // link materials
        request.setMaterials(
                new LinkedHashSet<>(materialRepository.findAllById(form.getMaterialIds()))
        );

        request.setStatus(RequestStatus.PENDING);

        requestRepository.save( request );
    }

    @Override
    public List<ReducedRequestDTO> getFutureWithStatus(RequestStatus status){
        return requestRepository.findFutureWithStatus(status).stream()
                .map( ReducedRequestDTO::toDto )
                .toList();
    }

    @Override
    public RequestDTO getRequestDetails(Long id) {
        return requestRepository.findById(id)
                .map(RequestDTO::toDto)
                .orElseThrow(() -> new NotFoundException(Request.class, id));
    }

    @Override
    public void refuseRequest(Long id, String justification) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Request.class, id));

        if(
                request.getStatus() != RequestStatus.PENDING &&
                request.getStatus() != RequestStatus.RELOCATING
        )
            throw new RequestStatusException();

        request.setStatus(RequestStatus.REFUSED);

        requestRepository.save(request);
    }

    @Override
    public void relocateRequest(Long id, String justification) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Request.class, id));

        if( request.getStatus() != RequestStatus.ACCEPTED )
            throw new RequestStatusException();

        request.setStatus(RequestStatus.RELOCATING);

        requestRepository.save(request);
    }

    @Override
    public void acceptRequest(Long requestId, Long roomId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(Request.class, requestId));

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException(Room.class, roomId));

        if(request.getNeededCapacity() <= room.getCapacity()
                && (request.getMadeBy().getRole().equals("TEACHER") || room.isStudentAccess())) {
            request.setRoom(room);

            request.setStatus(RequestStatus.ACCEPTED);

            requestRepository.save(request);
        }

        else throw new RuntimeException("Invalid choice: room cannot be attributed with specified request parameters");


    }


}
