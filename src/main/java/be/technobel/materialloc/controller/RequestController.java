package be.technobel.materialloc.controller;

import be.technobel.materialloc.models.dto.MaterialDTO;
import be.technobel.materialloc.models.dto.ReducedRequestDTO;
import be.technobel.materialloc.models.dto.RequestDTO;
import be.technobel.materialloc.models.dto.RoomDTO;
import be.technobel.materialloc.models.entity.RequestStatus;
import be.technobel.materialloc.models.form.RequestForm;
import be.technobel.materialloc.service.MaterialService;
import be.technobel.materialloc.service.RequestService;
import be.technobel.materialloc.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final MaterialService materialService;
    private final RequestService requestService;
    private final RoomService roomService;

    public RequestController(MaterialService materialService, RequestService requestService, RoomService roomService) {
        this.materialService = materialService;
        this.requestService = requestService;
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public List<ReducedRequestDTO> displayAllRequests(){
        return requestService.getFutureWithStatus(RequestStatus.PENDING);
    }

    @PostMapping("/new")
    public void processRequestForm(@RequestBody @Valid RequestForm form, Authentication auth){
        form.setUserLogin( (String)auth.getPrincipal() );
        requestService.create(form);
    }

    @GetMapping({
            "/{status:PENDING}",
            "/{status:REFUSED}",
            "/{status:ACCEPTED}",
            "/{status:RELOCATING}"
    })
    public List<ReducedRequestDTO> displayFutureRequests(@PathVariable RequestStatus status){
        return requestService.getFutureWithStatus(status);
    }

    @GetMapping("/{id:[0-9]+}")
    public RequestDTO displayRequestDetails(@PathVariable long id){
        return requestService.getRequestDetails(id);
    }

    @GetMapping("/{id:[0-9]+}/available-rooms")
    public List<RoomDTO> displayAvailableRooms(@PathVariable long id){
        return roomService.findCompatibleRoomsForRequest(id);
    }

    @PatchMapping("/{id:[0-9]+}/accept")
    public void assignRoom(@PathVariable long id, @RequestParam long roomId){
        requestService.acceptRequest(id, roomId);
    }

    @PatchMapping("/{id:[0-9]+}/refuse")
    public void refuse( @PathVariable long id, @RequestParam String justification ){
        requestService.refuseRequest(id, justification);
    }

    @PatchMapping("/{id:[0-9]+}/relocate")
    public void relocate( @PathVariable long id, @RequestParam String justification ){
        requestService.relocateRequest(id, justification);
    }


}