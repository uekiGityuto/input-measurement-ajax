package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Score;
import com.example.repository.ScoreRepository;

@Service
@Transactional
public class ScoreService {

	@Autowired
	ScoreRepository scoreRepository;

	public int receiveScore(String scoreString) {
		if (scoreString.isEmpty()) {
			return 0;
		} else {
			try {
				return Integer.parseInt(scoreString);
			} catch (NumberFormatException e) {
				return 0;
			}
		}

	}
	
	public void registerScore(Integer scoreInt) {
		Score score = new Score();
		score.setScoreInt(scoreInt);
		scoreRepository.save(score);
	}

	public List<Score> getScoreList() {
		return scoreRepository.findAllDescOrderByScoreNum();
	}

}
