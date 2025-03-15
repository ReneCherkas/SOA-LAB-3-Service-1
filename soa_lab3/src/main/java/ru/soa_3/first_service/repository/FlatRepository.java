package ru.soa_3.first_service.repository;

import ru.soa_3.first_service.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepository extends PagingAndSortingRepository<Flat, Integer>, JpaRepository<Flat, Integer>, JpaSpecificationExecutor<Flat> {

    @Query("SELECT AVG(f.numberOfRooms) FROM flat f")
    Double findAverageNumberOfRooms();

}
