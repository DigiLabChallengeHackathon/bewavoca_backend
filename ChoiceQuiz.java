@Data
@AllArgsConstructor
public static class ChoiceQuiz {

	/**
	 * 4지선다형 퀴즈 고유 ID.
	 */
	@NotBlank(message = "ChoiceId is required.")
	private String choiceId;

	/**
	 * 4지 선다 질문.
	 * 예: "___는 노란색이고 너무 달콤한 과일이야."
	 */
	@NotBlank(message = "Question is required.")
	private String question;

	/**
	 * 정답 선택지 배열.
	 * 예: ["바나나", "사과", "키위", "포도"]
	 */
	@NotNull(message = "Options are required.")
	private String[] options;

	/**
	 * 정답 제주어 단어.
	 * 예: "바나나"
	 */
	@NotBlank(message = "Correct answer is required.")
	private String correctAnswer;

	/**
	 * 정답 설명.
	 * 예: "바나나는 제주어로 A입니다."
	 */
	private String explanation;

	/**
	 * 음성 파일 경로.
	 * 예: "link_to_voice_file"
	 */
	private String voice;
}