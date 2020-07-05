package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.Score;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
	@Query("SELECT x FROM Score x ORDER BY x.scoreInt DESC")
	List<Score> findAllDescOrderByScoreNum();
}
