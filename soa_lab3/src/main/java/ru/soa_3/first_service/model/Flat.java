package ru.soa_3.first_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.soa_3.first_service.util.enums.Furnish;
import ru.soa_3.first_service.util.enums.Transport;
import ru.soa_3.first_service.util.enums.View;

import java.time.LocalDateTime;

@Entity(name = "flat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id", nullable = false)
    private Coordinates coordinates;

    @NotNull
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "area")
    private Float area;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "furnish")
    private Furnish furnish;

    @Enumerated(EnumType.STRING)
    @Column(name = "view")
    private View view;

    @Enumerated(EnumType.STRING)
    @Column(name = "transport")
    private Transport transport;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "house_id", referencedColumnName = "id", nullable = false)
    private House house;

    @Column(name = "price")
    private Double price;

    @Column(name = "time_to_metro_by_foot")
    private Integer timeToMetroByFoot;

    @Column(name = "time_to_metro_by_transport")
    private Integer timeToMetroByTransport;
}
