package com.nitinson.app.taskchecker;

import com.nitinson.app.taskchecker.rest.TaskController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskCheckerApplicationTests {

	@Autowired
	private TaskController taskController;

	@Test
	public void contextLoads() {
		assertThat(taskController).isNotNull();
	}

}
