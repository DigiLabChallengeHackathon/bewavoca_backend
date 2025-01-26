package com.mosabulgyeo.bewavoca.controller;

import com.mosabulgyeo.bewavoca.dto.ApiResponse;
import com.mosabulgyeo.bewavoca.dto.CompleteQuizRequest;
import com.mosabulgyeo.bewavoca.dto.QuizResponse;
import com.mosabulgyeo.bewavoca.service.AuthService;
import com.mosabulgyeo.bewavoca.service.QuizService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

	private final QuizService quizService;
	private final AuthService authService;

	public QuizController(QuizService quizService, AuthService authService) {
		this.quizService = quizService;
		this.authService = authService;
	}

	/**
	 * Fetches the quiz data based on level and type.
	 *
	 * @param level The level of the quiz (e.g., 1, 2, 3).
	 * @param type  The type of the quiz (e.g., ox, match, choice).
	 * @return The appropriate quiz response.
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<QuizResponse>> getQuiz(@RequestParam int level, @RequestParam String type) {
		if (level < 1) {
			throw new IllegalArgumentException("Level must be greater than 0.");
		}
		QuizResponse quizResponse = quizService.getQuizByTypeAndLevel(type, level);
		return ResponseEntity.ok(new ApiResponse<>(
			"success",
			"Quiz retrieved successfully",
			quizResponse
		));
	}

	/**
	 * 퀴즈 완료 처리
	 *
	 * @param request 퀴즈 완료 요청 데이터
	 * @return 완료 메시지
	 */
	@PostMapping("/complete")
	public ResponseEntity<ApiResponse<String>> completeQuiz(@RequestBody @Valid CompleteQuizRequest request) {
		switch (request.getResultStatus()) {
			case GREAT_SUCCESS:
				authService.clearStage(request.getDeviceId(), request.getRegion(), request.getStage());
				return ResponseEntity.ok(new ApiResponse<>(
					"success",
					"Quiz completed successfully with great success! Stage cleared.",
					null
				));
			case SUCCESS:
				authService.clearStage(request.getDeviceId(), request.getRegion(), request.getStage());
				return ResponseEntity.ok(new ApiResponse<>(
					"success",
					"Quiz completed successfully. Stage cleared.",
					null
				));
			case FAIL:
				return ResponseEntity.ok(new ApiResponse<>(
					"fail",
					"Quiz failed. Better luck next time!",
					null
				));
			default:
				throw new IllegalArgumentException("Invalid result status: " + request.getResultStatus());
		}
	}
}