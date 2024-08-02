package com.learning.quizapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.learning.quizapp.dao.QuestionDao;
import com.learning.quizapp.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public List<Question> getCategorizedQuestions(String category) {
		return questionDao.findByCategory(category);
	}

	public String addQuestion(@RequestBody Question question) {
		questionDao.save(question);	
		return "success";
	}

	public String deleteQuestion(int id) {
		questionDao.deleteById(id);
		return "deleted";
	}

	public String updateQuestion(int id, Question question) {
		Question q = questionDao.findById(id).get();
		q.setCategory(question.getCategory());
		q.setDifficultyLevel(question.getDifficultyLevel());
		q.setOption1(question.getOption1());
		q.setOption2(question.getOption2());
		q.setOption3(question.getOption3());
		q.setOption4(question.getOption4());
		q.setQuestionTitle(question.getQuestionTitle());
		q.setRightAnswer(question.getRightAnswer());
		questionDao.save(q);
		return "updated";
	}


}
