package com.atguigu.survey.entities.guest;

public class Answer {
	
	private Integer answerId;
	private String content;

	//表示参与调查的批次，同一个批次的答案是同一次参与产生的
	private String uuid;

	private Integer surveyId;
	private Integer questionId;

	public Answer() {
		
	}

	public Answer(Integer answerId, String content, String uuid,
			Integer surveyId, Integer questionId) {
		super();
		this.answerId = answerId;
		this.content = content;
		this.uuid = uuid;
		this.surveyId = surveyId;
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "Answer [answerId=" + answerId + "@ content=" + content
				+ "@ uuid=" + uuid + "@ surveyId=" + surveyId + "@ questionId="
				+ questionId + "]";
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

}
