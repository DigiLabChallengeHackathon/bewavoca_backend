package com.mosabulgyeo.bewavoca.controller;

import com.mosabulgyeo.bewavoca.dto.CompleteQuizRequest;
import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.service.QuizService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

	private final QuizService quizService;

	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	/**
	 * Fetches the quiz data based on level and type.
	 *
	 * @param level The level of the quiz (e.g., 1, 2, 3).
	 * @param type  The type of the quiz (e.g., ox, match, choice).
	 * @return The appropriate quiz response.
	 */
	@GetMapping
	public ResponseEntity<?> getQuiz(
		@RequestParam int level,
		@RequestParam String type
	) {
		try {
			QuizResponse quizResponse = quizService.getQuizByTypeAndLevel(type, level);
			return ResponseEntity.ok(quizResponse);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 퀴즈 완료 처리
	 *
	 * @param request 퀴즈 완료 요청 데이터
	 * @return 완료 메시지
	 */
	@PostMapping("/complete")
	public ResponseEntity<String> completeQuiz(@RequestBody CompleteQuizRequest request) {
		String message = quizService.completeQuiz(request);
		return ResponseEntity.ok(message);
	}
}