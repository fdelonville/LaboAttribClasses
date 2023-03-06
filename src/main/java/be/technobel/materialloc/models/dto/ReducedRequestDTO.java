package be.technobel.materialloc.models.dto;

import be.technobel.materialloc.models.entity.Request;
import be.technobel.materialloc.models.entity.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

@Data
public class ReducedRequestDTO implements Serializable {

    private final Long id;
    private final LocalDate date;
    private final LocalTime beginTime;
    private final LocalTime endTime;
    private final int neededCapacity;
    private final RequestStatus currentStatus;
    private final String madeBy;
    private final Integer roomNumber;

    public static ReducedRequestDTO toDto(Request entity){

        if(entity == null)
            return null;

        return new ReducedRequestDTO(
                entity.getId(),
                entity.getDate(),
                entity.getBeginTime(),
                entity.getEndTime(),
                entity.getNeededCapacity(),
                entity.getStatus(),
                entity.getMadeBy().getFirstName() + ' ' + entity.getMadeBy().getLastName().toUpperCase(),
                entity.getRoom() == null ? null : entity.getRoom().getNumber()
        );

    }


}
