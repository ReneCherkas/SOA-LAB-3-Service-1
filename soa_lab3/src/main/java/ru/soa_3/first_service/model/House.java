package ru.soa_3.first_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "house")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Min(value = 1, message = "House year должен быть больше 0!")
    @Max(value = 210, message = "Максимальное значение House year: 210")
    @Column(name = "year")
    private int year;

    @Min(value = 1, message = "House number of flats on floor должен быть больше 0!")
    @Column(name = "number_of_flats_on_floor")
    private long numberOfFlatsOnFloor;

    @Min(value = 1, message = "House number of lifts должен быть больше 0!")
    @Column(name = "number_of_lifts")
    private long numberOfLifts;
}
