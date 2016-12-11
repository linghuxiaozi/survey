package com.atguigu.survey.utils;

import java.util.HashSet;
import java.util.Set;

public class GlobalNames {
	
	public static final String LOGIN_USER = "loginUser";
	
	public static final Set<String> ALLOWED_TYPES = new HashSet<>();

	public static final String PAGE = "page";

	public static final String CURRENT_SURVEY = "currentSurvey";

	public static final String BAG_LIST = "bagList";

	public static final String ALL_BAG_MAP = "allBagMap";

	public static final String LAST_INDEX = "lastIndex";

	public static final String CURRENT_BAG = "currentBag";

	public static final String CURRENT_INDEX = "currentIndex";

	public static final String LOGIN_ADMIN = "loginAdmin";
	
	static {
		ALLOWED_TYPES.add("image/png");
		ALLOWED_TYPES.add("image/jpg");
		ALLOWED_TYPES.add("image/jpeg");
		ALLOWED_TYPES.add("image/pjpeg");
		ALLOWED_TYPES.add("image/gif");
	}

}
