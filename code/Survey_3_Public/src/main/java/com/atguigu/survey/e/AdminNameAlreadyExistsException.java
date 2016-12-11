package com.atguigu.survey.e;

public class AdminNameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AdminNameAlreadyExistsException(String message) {
		super(message);
	}

}
