package com.atguigu.survey.entities.guest;

import java.io.Serializable;

public class Question implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer questionId;
	private String questionName;
	private int questionType;//0:单选题,1:多选题,2:简答题
	private String options;
	
	private Bag bag;
	
	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Question(Integer questionId, String questionName, int questionType,
			String options, Bag bag) {
		super();
		this.questionId = questionId;
		this.questionName = questionName;
		this.questionType = questionType;
		this.options = options;
		this.bag = bag;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionName="
				+ questionName + ", questionType=" + questionType
				+ ", options=" + options + "]";
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getOptions() {
		return options;
	}
	
	//=================特殊设置=====================

	public void setOptions(String options) {
		/*this.options = options.replaceAll("\r\n", ",");
		
		while(this.options.contains(",,")) {
			this.options = this.options.replaceAll(",,", ",");
		}*/
		
		if(options == null || options.length() == 0) {
			this.options = "";
			return ;
		}
		
		options = "," + options.replaceAll("\r\n", ",") + ",";
		
		while(options.contains(",,")) {
			options = options.replaceAll(",,", ",");
		}
		
		this.options = options.substring(1, options.lastIndexOf(","));
		
	}
	
	public String[] getOptionArr() {
		
		return options.split(",");
	}
	
	public String getEditOption() {
		return options.replaceAll(",", "\r\n");
	}
	
	//============================================

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}
 
}
