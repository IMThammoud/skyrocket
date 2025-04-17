package com.skyrocket;

import com.skyrocket.controller.DynamicElementsController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SkyrocketApplicationTests {

	@Mock
	DynamicElementsController dynamicElementsController;

	@Test
	void contextLoads() {
	}
}
