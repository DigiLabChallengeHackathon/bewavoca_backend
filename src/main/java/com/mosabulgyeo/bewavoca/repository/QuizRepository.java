package com.mosabulgyeo.bewavoca.repository;

import com.mosabulgyeo.bewavoca.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
	List<Quiz> findByRegionIdAndStageType(Long regionId, String stageType);
}