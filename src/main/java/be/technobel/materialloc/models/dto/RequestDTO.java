package be.technobel.materialloc.models.dto;

import be.technobel.materialloc.models.entity.Request;
import be.technobel.materialloc.models.entity.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Data
public class RequestDTO implements Serializable {

    private final Long id;
    private final LocalDateTime beginAt;
    private final LocalDateTime endAt;
    private final int neededCapacity;
    private final String justification;
    private final String additionalNotes;
    private final RequestStatus status;
    private final PersonDTO madeBy;
    private final RoomDTO room;

    public static RequestDTO toDto(Request entity){
        if( entity == null )
            return null;

        return new RequestDTO(
                entity.getId(),
                entity.getDate().atTime(entity.getBeginTime()),
                entity.getDate().atTime(entity.getEndTime()),
                entity.getNeededCapacity(),
                entity.getJustification(),
                entity.getAdditionalNotes(),
                entity.getStatus(),
                PersonDTO.toDto(entity.getMadeBy()),
                RoomDTO.toDto(entity.getRoom())
        );


    }

}
