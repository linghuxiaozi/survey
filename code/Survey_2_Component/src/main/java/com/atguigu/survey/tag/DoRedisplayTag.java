package com.atguigu.survey.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.atguigu.survey.utils.GlobalNames;

public class DoRedisplayTag extends SimpleTagSupport{
	
	private Integer bagId;
	private String paramName;
	private Integer questionType;
	private String currentValue;
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//1.准备工作
		//①获取Web资源对象
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		HttpSession session = pageContext.getSession();
		
		//②从Session域中获取allBagMap
		Map<Integer, Map<String, String[]>> allBagMap = 
				(Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//③根据bagId从allBagMap中获取当前包裹以前提交的请求参数Map
		Map<String, String[]> paramMap = allBagMap.get(bagId);
		
		//如果当前包裹没有保存过paramMap，那么当前方法停止执行
		if(paramMap == null) {
			return ;
		}
		
		//④根据请求参数name值从paramMap中获取请求参数value值
		String[] paramValues = paramMap.get(paramName);
		
		//如果paramValues是null，说明以前来这个包裹时没有提交过数据，那么就不需要再判断是否要回显了
		if(paramValues == null) {
			return ;
		}
		
		//⑤判断当前题型
		if(questionType == 2) {
			
			//如果是简答题
			String redisplayValue = paramValues[0];
			out.print("value='"+redisplayValue+"'");
			
		}else{
			
			//单选和多选的判断方式一致
			//检查“当前radio或checkbox”的value属性值是否在paramValues数组中
			//i.将paramValues数组转换成List集合
			List<String> paramList = Arrays.asList(paramValues);
			
			//ii.判断是否在
			if(paramList.contains(currentValue)) {
				out.print("checked='checked'");
			}
			
		}
		
	}
	
	public void setBagId(Integer bagId) {
		this.bagId = bagId;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
}
