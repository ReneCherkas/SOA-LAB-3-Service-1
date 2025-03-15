package ru.soa_3.first_service.service;

import lombok.RequiredArgsConstructor;
import ru.soa_3.first_service.model.Flat;
import ru.soa_3.first_service.repository.FlatRepository;
import ru.soa_3.first_service.util.enums.Furnish;
import ru.soa_3.first_service.util.enums.Transport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdditionalOperationsService {

    private final FlatRepository flatRepository;

    public Double getAverageNumberOfRooms() {
        return flatRepository.findAverageNumberOfRooms();
    }

    public long countFlatsWithTransportGreaterThan(Transport transport) {
        List<Flat> flats = flatRepository.findAll();
        return flats.stream()
                .filter(flat -> flat.getTransport().isGreaterThan(transport))
                .count();
    }

    public List<Flat> getFlatsWithFurnishGreaterThan(Furnish furnish) {
        List<Flat> flats = flatRepository.findAll();

        return flats.stream()
                .filter(flat -> flat.getFurnish().isGreaterThan(furnish))
                .collect(Collectors.toList());
    }
}
