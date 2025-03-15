package ru.soa_3.first_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.soa_3.first_service.dto.FlatDTO;
import ru.soa_3.first_service.dto.FlatsDTO;
import ru.soa_3.first_service.exception.ErrorDefault;
import ru.soa_3.first_service.model.Flat;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.soa_3.first_service.service.FlatService;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/flats", produces = MediaType.APPLICATION_XML_VALUE)
public class FlatController {

    private final FlatService flatService;

    @GetMapping
    public ResponseEntity<?> getFlats(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "1000") Integer size,
            @RequestParam(required = false) List<String> sort,
            @RequestParam(required = false) List<String> filter) {

        Page<FlatsDTO> flats = flatService.getFlats(page, size, sort, filter);
        return flats.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDefault("No flats found for the specified filters."))
                : ResponseEntity.ok(flats);
    }

    @PostMapping
    public ResponseEntity<?> createFlat(@Valid @RequestBody FlatDTO flatDTO) {
        flatService.add(flatDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlat(@PathVariable int id) {
        Flat flat = flatService.findById(id);
        return ResponseEntity.ok(flat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlat(@PathVariable Integer id, @Valid @RequestBody FlatDTO flatDTO) {
        Flat updatedFlat = flatService.update(id, flatService.createFromFlatDTO(flatDTO));
        return ResponseEntity.ok(updatedFlat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlat(@PathVariable int id) {

        flatService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 204);
        response.put("message", "The flat was successfully deleted");
        response.put("time", ZonedDateTime.now(ZoneOffset.UTC).toString());

        return ResponseEntity.status(204).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFlats() {
        List<Flat> flats = flatService.findAll();
        if (flats.isEmpty()) {
            ErrorDefault errorResponse = new ErrorDefault("No flats found for the specified filters.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(flats);
    }
}
