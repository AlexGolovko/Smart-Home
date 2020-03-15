package com.golovkobalak.smarthome.repo;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@DataMongoTest
@SpringBootTest
class MeasureRepositoryTest {

    @Autowired
    private MeasureRepository repo;
    @Autowired
    private Measure measure;

    @Test
    public void saveReadAndDeleteTest() {
        assertNotNull(repo);
        var map = new ConcurrentHashMap<String, String>();
        map.put("sensors/temperature", "24");
        map.put("sensors/time", new DateTime().toString());
        measure.setMeasures(map);
        repo.save(measure);
        var all = repo.findById(measure.getId());
        assertTrue(all.isPresent());
        repo.delete(measure);
        assertTrue(repo.findById(measure.getId()).isEmpty());

        //Optional<Measure> foundMeasure = repo.findById(uuid);
        //assertEquals(measure, foundMeasure);
    }
}