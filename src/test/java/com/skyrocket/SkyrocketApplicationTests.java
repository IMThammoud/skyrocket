package com.skyrocket;

import com.skyrocket.controller.DynamicElementsController;
import com.skyrocket.services.UserAccountQueries;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SkyrocketApplicationTests {

	@Mock
	UserAccountQueries userAccountQueries;

	@Mock
	DynamicElementsController dynamicElementsController;

	@Test
	void contextLoads() {
	}
}
