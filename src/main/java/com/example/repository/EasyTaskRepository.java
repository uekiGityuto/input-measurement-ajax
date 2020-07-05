package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.EasyTask;

public interface EasyTaskRepository extends JpaRepository<EasyTask, Integer>{

}
