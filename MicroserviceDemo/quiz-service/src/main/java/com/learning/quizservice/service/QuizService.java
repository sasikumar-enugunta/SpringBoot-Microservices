package com.learning.quizservice.service;

import com.learning.quizservice.dao.QuizDao;
import com.learning.quizservice.feign.QuizInterface;
import com.learning.quizservice.model.QuestionWrapper;
import com.learning.quizservice.model.Quiz;
import com.learning.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

	@Autowired
	QuizDao quizDao;

	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String categoryName, int numQ, String title) {
		List<Integer> questions = quizInterface.getQuestionsForQuiz(categoryName, numQ).getBody();

		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionsId(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
		Quiz quiz = quizDao.findById(id).get();
		List<Integer> questionsId = quiz.getQuestionsId();
		List<QuestionWrapper> questionsForUser = quizInterface.getQuestionsFromId(questionsId).getBody();
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
	}

	public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
		return quizInterface.getScore(responses);
	}

}
