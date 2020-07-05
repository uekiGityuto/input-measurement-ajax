package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.EasyTask;
import com.example.repository.EasyTaskRepository;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	EasyTaskRepository easyTaskRepository;
	
	public long getEasyTaskNum() {
		return easyTaskRepository.count();
	}
	
	public String getEasyTaskStatement(Integer id) {
		Optional<EasyTask> optionalTask = easyTaskRepository.findById(id);
		EasyTask easyTask = optionalTask.orElseThrow(() -> new IllegalArgumentException("該当の課題は存在しません"));
		return easyTask.getStatement();
	}
	
	public boolean isCollectAnswer(String task, String answer) {
		if(answer.equals(task)) {
			return true;
		} else {
			return false;
		}
	}
}
