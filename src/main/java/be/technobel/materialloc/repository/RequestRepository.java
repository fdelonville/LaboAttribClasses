package be.technobel.materialloc.repository;

import be.technobel.materialloc.models.entity.Request;
import be.technobel.materialloc.models.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("""
        SELECT r
        FROM Request r
        WHERE r.date >= now() AND r.status = :status
        """)
    List<Request> findFutureWithStatus(RequestStatus status);

}
