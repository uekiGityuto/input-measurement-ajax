package com.example.web;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnswerForm {
	
	private String task;
	private String answer;
	@NotNull
	private String score;
}
