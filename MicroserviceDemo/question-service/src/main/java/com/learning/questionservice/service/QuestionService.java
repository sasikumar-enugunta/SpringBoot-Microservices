package com.learning.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import com.learning.questionservice.dao.QuestionDao;
import com.learning.questionservice.model.Question;
import com.learning.questionservice.model.QuestionWrapper;
import com.learning.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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


	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsId) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();

		for(Integer id : questionsId) {
			Question q = questionDao.findById(id).get();
			questions.add(q);
		}

		for(Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			wrappers.add(wrapper);
		}

		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int result = 0;
		for(Response response : responses) {
			Question question = questionDao.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer())) {
				result += 1;
			}
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
