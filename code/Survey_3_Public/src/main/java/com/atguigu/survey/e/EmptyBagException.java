package com.atguigu.survey.e;

public class EmptyBagException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public EmptyBagException(String message) {
		super(message);
	}

}
