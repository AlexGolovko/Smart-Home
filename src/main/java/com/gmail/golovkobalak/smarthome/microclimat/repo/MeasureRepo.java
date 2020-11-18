package com.gmail.golovkobalak.smarthome.microclimat.repo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureRepo extends MongoRepository<Measure, String> {

    Measure findTopByOrderByDateDesc();
}
