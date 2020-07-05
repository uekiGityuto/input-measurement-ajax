package com.example.web;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Score;
import com.example.service.ScoreService;
import com.example.service.TaskService;
import com.google.gson.Gson;

@Controller
public class WebController {

	@Autowired
	TaskService taskService;
	@Autowired
	ScoreService scoreService;
	@Autowired
	HttpSession session;

	@GetMapping("/index")
	public String gotoIndex() {
		return "index";
	}

	@GetMapping("/start")
	@ResponseBody
	public String start(ReturnPOJO returnPOJO) {
		
		// 出題する課題番号をランダムに選択
		long taskNum = taskService.getEasyTaskNum();
		Random rand = new Random();
		int taskId = rand.nextInt((int) taskNum) + 1;

		// 選択した課題番号から課題を取得してJSON形式で応答
		String taskStatement = taskService.getEasyTaskStatement(taskId);
		returnPOJO.setTask(taskStatement);
		Gson gson = new Gson();
		return gson.toJson(returnPOJO);

	}

	@PostMapping("/answer")
	@ResponseBody
	public String answer(@Validated @RequestBody AnswerForm form, 
			ReturnPOJO returnPOJO) {

		// 獲得済みのscoreを受け取る、初回の場合はscoreに0をセットする
		Integer scoreInt = scoreService.receiveScore(form.getScore());

		// 回答が正しければscoreに1加算する
		if(taskService.isCollectAnswer(form.getTask(), form.getAnswer())) {
			scoreInt++;
		}

		returnPOJO.setScore(scoreInt);
		return this.start(returnPOJO);

	}

	@PostMapping("/result")
	public String result(@Validated AnswerForm form, Model model) {
		// 獲得済みのscoreを受け取る、一度も回答していない場合はscoreに0をセットする
		Integer scoreInt = scoreService.receiveScore(form.getScore());

		// scoreをDBに登録
		scoreService.registerScore(scoreInt);
		
		// 画面に渡すデータをmodelにセット
		List<Score> scoreList = scoreService.getScoreList();
		model.addAttribute("scoreList", scoreList);
		model.addAttribute("yourScore", scoreInt);
		return "result";
	}
	
	@GetMapping("/result")
	public String result(Model model) {
		
		return "redirect:/index?error";
		
	}

}
