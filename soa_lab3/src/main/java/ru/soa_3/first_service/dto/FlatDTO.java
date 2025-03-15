package ru.soa_3.first_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.soa_3.first_service.model.Coordinates;
import ru.soa_3.first_service.model.House;
import ru.soa_3.first_service.util.enums.Furnish;
import ru.soa_3.first_service.util.enums.Transport;
import ru.soa_3.first_service.util.enums.View;

@Data
public class FlatDTO {
    @NotBlank(message = "Name не может быть пустым!")
    @Size(max = 255, message = "Name не может превышать длину 255 символов!")
    private String name;

    @NotNull(message = "Coordinates не может быть пустым!")
    private Coordinates coordinates;

    @Min(value = 1, message = "Area должен быть больше 0!")
    private Float area;

    @Min(value = 1, message = "Number Of Rooms должен быть больше 0!")
    private Integer numberOfRooms;

    private Furnish furnish;

    @NotNull(message = "View не может быть пустым!")
    private View view;

    @NotNull(message = "Transport не может быть пустым!")
    private Transport transport;

    @NotNull(message = "House не может быть пустым!")
    private House house;

    @Min(value = 1, message = "Price должен быть больше 0!")
    private Double price;

    @Min(value = 1, message = "Time to metro by foot должен быть больше 0!")
    private Integer timeToMetroByFoot;

    @Min(value = 1, message = "Time to metro by transport должен быть больше 0!")
    private Integer timeToMetroByTransport;
}