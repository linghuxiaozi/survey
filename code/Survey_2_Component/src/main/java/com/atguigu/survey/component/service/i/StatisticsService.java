package com.atguigu.survey.component.service.i;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

import com.atguigu.survey.base.i.BaseService;
import com.atguigu.survey.entities.guest.Survey;

public interface StatisticsService extends BaseService<Survey>{

	List<String> readGetTextList(Integer questionId);

	JFreeChart readGenerateChart(Integer questionId);

	HSSFWorkbook readGenerateWorkBook(Integer surveyId);

}
