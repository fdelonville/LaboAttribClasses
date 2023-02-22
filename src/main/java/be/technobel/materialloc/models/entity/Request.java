package be.technobel.materialloc.models.entity;

import be.technobel.materialloc.models.entity.users.Admin;
import be.technobel.materialloc.models.entity.users.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime beginTime;
    @Column(nullable = false)
    private LocalTime endTime;

    @Column(name = "needed_capacity", nullable = false)
    private int neededCapacity;

    @Column(name = "justification", nullable = false)
    private String justification;

    @Column(name = "additionnal_notes")
    private String additionalNotes;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private Set<Status> statusHistory = new LinkedHashSet<>();

    @ManyToMany
    private Set<Material> materials = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "made_by_id", nullable = false)
    private Person madeBy;

    @ManyToOne
    private Room room;

}
