package com.example.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(properties = { "spring.datasource.url:jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE;" })
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class WebControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGotoIndex() throws Exception {
		log.info("テストNo.1開始");
		mockMvc.perform(get("/index"))
			.andExpect(status().isOk())
			.andExpect(view().name("index"));
		log.info("テストNo.1終了");
	}
	
	@Test
	@Sql(scripts = "classpath:/sql/test-drop-easytbl.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/sql/test-create-easytbl.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/sql/test-data-create-one-easy-task.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testStart() throws Exception {
		log.info("テストNo.2開始");
		mockMvc.perform(get("/start"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/index"))
		//.andExpect(model().attribute("taskStatement", "テスト"));
		.andExpect(flash().attribute("taskStatement", "テスト"));
		log.info("テストNo.2終了");
	}

}
