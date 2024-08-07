package com.learning.questionservice.controller;

import java.util.List;

import com.learning.questionservice.model.Question;
import com.learning.questionservice.model.QuestionWrapper;
import com.learning.questionservice.model.Response;
import com.learning.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("question")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;

	@Autowired
	Environment environment;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}
	
	@GetMapping("category/{category}")
	public List<Question> getCategorizedQuestions(@PathVariable String category) {
		return questionService.getCategorizedQuestions(category);
	}
	
	@PostMapping("addQuestion")
	public String addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}
	
	@DeleteMapping("delete/{id}")
	public String deleteQuestion(@PathVariable int id) {
		return questionService.deleteQuestion(id);
	}

	@PutMapping("update/{id}")
	public String updateQuestion(@PathVariable int id, @RequestBody Question question) {
		return questionService.updateQuestion(id, question);
	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam int numQuestions) {
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}

	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId) {
		// System.out.println(environment.getProperty("local.server.port")); -- to check which instance is running among multiple in single service
		return questionService.getQuestionsFromId(questionsId);
	}

	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
		return questionService.getScore(responses);
	}

}
