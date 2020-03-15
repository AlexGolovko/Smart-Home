package com.golovkobalak.smarthome.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureRepository extends MongoRepository<Measure, String> {
}
