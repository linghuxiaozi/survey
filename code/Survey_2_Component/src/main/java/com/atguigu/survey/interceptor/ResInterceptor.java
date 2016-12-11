package com.atguigu.survey.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataprocessUtils;

/**
 * 开发阶段临时使用的拦截器，当项目不再产生新的功能时可以取消这个拦截器
 * @author Creathin
 *
 */
public class ResInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		
		//1.获取当前请求的servletPath
		String servletPath = request.getServletPath();
		
		//2.将servletPath中附加数据的部分去掉
		servletPath = DataprocessUtils.cutServletPath(servletPath);
		
		//3.检查这个servletPath是否已经保存到数据库中
		boolean exists = resService.readCheckServletPath(servletPath);
		
		//4.如果已经保存了，那么放行
		if(exists) {
			return true;
		}
		
		//5.如果没有保存，那么根据这个servletPath创建Res对象保存到数据库
		//①声明两个变量作为最终的权限码、权限位，初始值是第一次保存时使用的默认值
		//权限位:0
		int finalResPos = 0;
		
		//权限码:1
		int finalResCode = 1;
		
		//②查询当前系统中最大的权限位
		Integer maxPos = resService.readGetMaxPos();
		
		//③查询在当前最大权限位的范围内最大的权限码
		Integer maxCode = (maxPos == null)?null:resService.readGetMaxCode(maxPos);
		
		//④判断权限码和权限位是否存在，如果存在则继续执行下面的判断
		if(maxPos != null && maxCode != null) {
			int systemMaxCode = 1 << 30;
			
			//⑤判断权限码是否已经达到了最大值，如果已经达到最大值
			if(maxCode >= systemMaxCode) {
				//权限位+1
				finalResPos = maxPos + 1;
				
				//权限码设置为1
				finalResCode = 1;
				
			}else{
				//⑥如果权限码没有达到最大值
				//权限位不变
				finalResPos = maxPos;
				
				//权限码左移1位
				finalResCode = maxCode << 1;
				
			}
			
		}
		
		//⑥创建Res对象
		Res res = new Res(null, servletPath, finalResCode, finalResPos, false);
		
		//⑦保存Res对象
		resService.writeSaveEntity(res);
		
		return true;
	}

}
