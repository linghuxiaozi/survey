package com.atguigu.survey.log.aspect;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.RequestBinder;
import com.atguigu.survey.utils.TokenBinder;

/**
 * 日志记录仪
 * @author Creathin
 *
 */
public class LogRecorder {
	
	@Autowired
	private LogService logService;
	
	public Object logRecord(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object targetResult = null;
		String methodName = null;
		String typeName = null;
		Object[] args = null;
		String exceptionType = null, exceptionMessage = null;
		
		try{
			
			//获取目标方法执行时传入的实际参数
			args = joinPoint.getArgs();
			
			//获取目标方法的签名
			Signature signature = joinPoint.getSignature();
			
			//获取目标方法的方法名
			methodName = signature.getName();
			
			//获取目标方法所在的类型名
			typeName = signature.getDeclaringTypeName();
			
			//调用目标对象的目标方法
			targetResult = joinPoint.proceed(args);
			
		}catch(Throwable e){
			
			//收集异常信息
			exceptionType = e.getClass().getName();
			exceptionMessage = e.getMessage();
			
			//当前捕获到的异常有可能是其他异常所引起的，也就是说有其他原因
			//尝试获取异常的原因对象
			Throwable cause = e.getCause();
			while(cause != null) {
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
				
				//进一步尝试获取“原因的原因”
				cause = cause.getCause();
				
				//无限死循环
				//cause = e.getCause();
			}
			
			/*for(Throwable cause = e.getCause();cause != null;cause = cause.getCause()) {
				exceptionType = cause.getClass().getName();
				exceptionMessage = cause.getMessage();
			}*/
			
			//目标方法抛出的异常，在通知方法中需要继续向上抛出
			throw e;
			
		}finally{
			
			//1.获取用户信息
			HttpServletRequest request = RequestBinder.getRequest();
			HttpSession session = request.getSession();
			
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_ADMIN);
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			
			String adminPart = (admin==null)?"管理员未登录":admin.getAdminName();
			String userPart = (user==null)?"用户未登录":user.getUserName();
			
			String operator = adminPart+"/"+userPart;
			
			//2.操作时间
			String operateTime = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
			
			//3.将args转换为parameters
			//数组的toString()方法：[Ljava.lang.String@234325
			//List的toString()方法：[1,2,3]
			String parameters = (args == null)?"无入参":Arrays.asList(args).toString();
			
			//4.将targetResult转换为returnValue
			String returnValue = (targetResult == null) ? "无返回值" : targetResult.toString();
			
			Log log = new Log(null, 
								operator, 
								operateTime, 
								methodName, 
								typeName, 
								parameters, 
								returnValue, 
								exceptionType, 
								exceptionMessage);
			
			//logService.writeSaveEntity(log);
			
			TokenBinder.bindToke(TokenBinder.KEY_LOG);
			logService.writeSaveLog(log);
			
		}
		
		//将目标方法的返回值返回给上层的调用方法
		return targetResult;
	}

}
