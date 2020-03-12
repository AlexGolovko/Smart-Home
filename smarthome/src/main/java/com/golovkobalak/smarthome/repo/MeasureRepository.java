package com.golovkobalak.smarthome.repo;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends PagingAndSortingRepository<Measure,Long> {
}
