package com.golovkobalak.smarthome;

import com.golovkobalak.smarthome.repo.Measure;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmarthomeApplicationTests {
	@Autowired
	private Measure measure;

	@Test
	void contextLoads() {
		assertNotNull(measure);
	}

}
