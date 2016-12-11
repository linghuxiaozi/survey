package com.atguigu.survey.entities.guest;

import java.util.Set;

public class Survey {
	
	private Integer surveyId;
	private String surveyName;

	//考虑到用户可以不指定Logo图片，所以设置默认值
	private String logoPath="res_static/logo.gif";
	private boolean completed=false;
	private User user;
	
	private Set<Bag> bagSet;
	
	public Survey() {
		// TODO Auto-generated constructor stub
	}

	public Survey(Integer surveyId) {
		super();
		this.surveyId = surveyId;
	}

	public Survey(Integer surveyId, String surveyName, String logoPath,
			boolean completed, User user) {
		super();
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.logoPath = logoPath;
		this.completed = completed;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", surveyName=" + surveyName
				+ ", logoPath=" + logoPath + ", completed=" + completed + "]";
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Bag> getBagSet() {
		return bagSet;
	}

	public void setBagSet(Set<Bag> bagSet) {
		this.bagSet = bagSet;
	}

}
