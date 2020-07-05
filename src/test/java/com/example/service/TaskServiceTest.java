package com.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = 
{ "spring.datasource.url:jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE;" })
class TaskServiceTest {
	
	@Autowired
	TaskService taskService;

	@Test
	@Sql(scripts = "classpath:/sql/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/sql/test-data-create-easy-task.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testGetEasyTaskNum() {
		log.info("テストNo.1開始");
		log.info("課題数{}", taskService.getEasyTaskNum());
		long easyTaskNum = taskService.getEasyTaskNum();
		assertEquals(5, easyTaskNum);
		log.info("テストNo.1終了");
	}
	
	@Test
	@Sql(scripts = "classpath:/sql/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	@Sql(scripts = "classpath:/sql/test-data-create-easy-task.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testGetEasyTaskStatement() {
		log.info("テストNo.2開始");
		log.info("課題数{}", taskService.getEasyTaskNum());
		assertEquals("テスト", taskService.getEasyTaskStatement(1));
		assertEquals("課題", taskService.getEasyTaskStatement(2));
		assertEquals("おにやんま", taskService.getEasyTaskStatement(3));
		assertEquals("かえる", taskService.getEasyTaskStatement(4));
		assertEquals("らっきー", taskService.getEasyTaskStatement(5));
		log.info("テストNo.2終了");
	}
	
	@Test
	@Sql(scripts = "classpath:/sql/test-data-init.sql", config = @SqlConfig(encoding = "utf-8"))
	//@Sql(scripts = "classpath:/sql/test-data-create-easy-task.sql", config = @SqlConfig(encoding = "utf-8"))
	public void testGetEasyTaskStatementExeception() {
		log.info("テストNo.3開始");
		log.info("課題数{}", taskService.getEasyTaskNum());
		try {
			String statement = taskService.getEasyTaskStatement(6);
			log.info("6行目の課題:{}", statement);
		} catch(IllegalArgumentException e) {
			assertEquals("該当の課題は存在しません", e.getMessage());
			log.info("テストNo.3終了(ok)");
			return;
		}
		fail();
		log.info("テストNo.3終了(ng)");
	}

	
}
