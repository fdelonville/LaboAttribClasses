package be.technobel.materialloc.repository;

import be.technobel.materialloc.models.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // récupère les salles libre entre 2 instants,
    // d'une capacité plus grande que désirée
    // disponible pour prof
    @Query("""
        SELECT r
        FROM Room r
            JOIN Request req ON r.id = req.room.id
            JOIN Status status ON status.request.id = req.id
        WHERE
            r.capacity >= :capacity AND (
                req.date != :begin_date OR
                (req.beginTime > :end_time AND req.endTime < :begin_time)
            )
    """)
    List<Room> searchRoomForTeacher(
            int capacity,
            LocalDate begin_date,
            LocalTime begin_time,
            LocalTime end_time
    );

}
