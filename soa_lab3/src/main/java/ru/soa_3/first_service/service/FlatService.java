package ru.soa_3.first_service.service;

import lombok.RequiredArgsConstructor;
import ru.soa_3.first_service.dto.FlatDTO;
import ru.soa_3.first_service.dto.FlatsDTO;
import ru.soa_3.first_service.exception.ResourceNotFoundException;
import ru.soa_3.first_service.model.Coordinates;
import ru.soa_3.first_service.model.Flat;
import ru.soa_3.first_service.model.House;
import ru.soa_3.first_service.repository.FlatRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlatService {
    private final FlatRepository flatRepository;

    private Pageable createPageable(Integer page, Integer size, Sort sort) {
        if (page != null && page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size != null && size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : Integer.MAX_VALUE;

        return PageRequest.of(pageNumber, pageSize, sort);
    }
    public List<Flat> findAll() {
        return flatRepository.findAll();
    }
    public Page<FlatsDTO> getFlats(Integer page, Integer size, List<String> sort, List<String> filters) {
        Sort sortSpec = FlatSpecification.buildSort(sort != null ? sort : List.of());
        Pageable pageable = createPageable(page, size, sortSpec);
        Specification<Flat> specification = FlatSpecification.createSpecification(filters);

        Page<Flat> flatsPage = flatRepository.findAll(specification, pageable);

        return flatsPage.map(flat -> {
            Coordinates coordinates = flat.getCoordinates();
            House house = flat.getHouse();

            return new FlatsDTO(flat, coordinates, house);
        });
    }

    public Flat add(FlatDTO flatDTO) {
        return flatRepository.save(createFromFlatDTO(flatDTO));
    }

    public Flat findById(Integer id) {
        return flatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flat with id " + id + " not found"));
    }

    public Flat update(Integer id, Flat flat) {
        Flat flatToUpdate = flatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flat with id " + id + " not found"));

        flatToUpdate.setName(flat.getName());
        Coordinates coordinates = flatToUpdate.getCoordinates();
        coordinates.setX(flat.getCoordinates().getX());
        coordinates.setY(flat.getCoordinates().getY());
        flatToUpdate.setArea(flat.getArea());
        flatToUpdate.setNumberOfRooms(flat.getNumberOfRooms());
        flatToUpdate.setFurnish(flat.getFurnish());
        flatToUpdate.setView(flat.getView());
        flatToUpdate.setTransport(flat.getTransport());
        House house = flatToUpdate.getHouse();
        house.setName(flat.getHouse().getName());
        house.setYear(flat.getHouse().getYear());
        house.setNumberOfFlatsOnFloor(flat.getHouse().getNumberOfFlatsOnFloor());
        house.setNumberOfLifts(flat.getHouse().getNumberOfLifts());
        flatToUpdate.setPrice(flat.getPrice());
        flatToUpdate.setTimeToMetroByFoot(flat.getTimeToMetroByFoot());
        flatToUpdate.setTimeToMetroByTransport(flat.getTimeToMetroByTransport());
        flatRepository.save(flatToUpdate);
        return flatToUpdate;
    }

    public void delete(int id) {
        Flat flat = flatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flat not found with id: " + id));
        flatRepository.delete(flat);
    }


    public Flat createFromFlatDTO(FlatDTO flatDTO) {

        return Flat.builder()
                .name(flatDTO.getName())
                .coordinates(flatDTO.getCoordinates())
                .area(flatDTO.getArea())
                .numberOfRooms(flatDTO.getNumberOfRooms())
                .furnish(flatDTO.getFurnish())
                .view(flatDTO.getView())
                .transport(flatDTO.getTransport())
                .house(flatDTO.getHouse())
                .price(flatDTO.getPrice())
                .creationDate(LocalDateTime.now())
                .timeToMetroByFoot(flatDTO.getTimeToMetroByFoot())
                .timeToMetroByTransport(flatDTO.getTimeToMetroByTransport())
                .build();
    }
}

