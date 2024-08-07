package com.learning.questionservice.dao;

import java.util.List;

import com.learning.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

	List<Question> findByCategory(String category);

	@Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
