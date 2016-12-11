package com.atguigu.survey.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.entities.manager.Role;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class DataprocessUtils {
	
	/**
	 * 生成字符串字符串的工具方法
	 * @param tableNameList
	 * @return
	 * 
	 *SELECT * FROM survey_log_2016_09 UNION
	 *SELECT * FROM survey_log_2016_10 UNION
	 *SELECT * FROM survey_log_2016_11 UNION
	 *SELECT * FROM survey_log_2016_12
	 */
	public static String createSubQuery(List<String> tableNameList) {
		
		if(tableNameList == null || tableNameList.size() == 0) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (String tableName : tableNameList) {
			//tableName本身没有空格，但和前后关键词之间需要使用空格分开
			builder.append("SELECT * FROM ").append(tableName).append(" UNION ");
		}
		
		int index = builder.lastIndexOf(" UNION ");
		
		return builder.substring(0, index);
	}
	
	/**
	 * 自动生成日志表名
	 * @param offset
	 * 		负数：以前的月份
	 * 		0：当前月
	 * 		正数：以后的月份
	 * @return
	 */
	public static String generateTableName(int offset) {
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.MONTH, offset);
		
		Date time = calendar.getTime();
		
		//※注意：这里千万不要使用“-”，因为在执行SQL语句时这个符号会被解析为减法操作
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM");
		
		return "SURVEY_LOG_"+format.format(time);
	}
	
	/**
	 * 根据Role集合计算权限码数组，同时适用于User或Admin
	 * @param roleSet
	 * @return
	 */
	public static String calculateCodeArr(Set<Role> roleSet, Integer maxPos) {
		
		if(roleSet == null || roleSet.size() == 0) {
			return null;
		}
		
		//[0]查询当前系统中最大的权限位的值：外部传入
		//[1]声明一个数组，长度是系统中最大的权限位的值，用于保存各个权限位上面最终的权限码
		//			Integer[]：每一个元素的默认值是null
		//			int[]：每一个元素的默认值是0，可以直接用于进行或运算，不必判断
		int[] codeArr = new int[maxPos+1];
		
		//[2]从Set<Role>开始进行逐层遍历
		for (Role role : roleSet) {
			Set<Auth> authSet = role.getAuthSet();
			for (Auth auth : authSet) {
				Set<Res> resSet = auth.getResSet();
				//[3]得到每一个Res对象
				for (Res res : resSet) {
					//[4]将Res对象的权限码、权限位取出
					int resCode = res.getResCode();
					int resPos = res.getResPos();
					
					//[5]以权限位为数组下标，从[1]声明的数组中取值——旧值
					int oldValue = codeArr[resPos];
					
					//[6]将旧值和Res对象的权限码值进行或运算——新值
					int newValue = oldValue | resCode;
					
					//[7]将新值放回数组中原来的位置
					codeArr[resPos] = newValue;
					
				}
			}
		}
		
		//[8]将数组转换成字符串
		return convertCodeArr2Str(codeArr);
	}
	
	public static String convertCodeArr2Str(int[] codeArr) {
		
		if(codeArr == null || codeArr.length == 0) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < codeArr.length; i++) {
			builder.append(codeArr[i]).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}
	
	/**
	 * 借助序列化和反序列化技术实现深度复制
	 * @param source
	 * @return
	 */
	public static Serializable deeplyCopy(Serializable source) {
		
		//1.声明四个变量，用于接收四个流对象
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		
		try{
			
			//2.创建字节数组输出流
			baos = new ByteArrayOutputStream();
			
			//3.创建对象输出流
			oos = new ObjectOutputStream(baos);
			
			//4.执行序列化操作：将源对象写入到对象输出流中，实际上是写入到了一个字节数组中
			oos.writeObject(source);
			
			//5.通过字节数组输出流获取包含对象二进制数据的字节数组
			byte[] byteArray = baos.toByteArray();
			
			//6.根据字节数组对象创建字节数组输入流
			bais = new ByteArrayInputStream(byteArray);
			
			//7.根据字节数组输入流创建对象输入流
			ois = new ObjectInputStream(bais);
			
			//8.使用对象数组输入流执行反序列化操作
			return (Serializable) ois.readObject();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}finally{
			
			//9.释放资源
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return null;
	}
	
	/**
	 * 检查包裹序号集合中是否存在重复元素
	 * @param bagOrderList
	 * @return
	 * 	true：没有重复
	 * 	false：重复
	 */
	public static boolean checkListDuplicate(List<Integer> bagOrderList) {
		
		Set<Integer> bagOrderSet = new HashSet<>(bagOrderList);
		
		return bagOrderList.size() == bagOrderSet.size();
	}
	
	/**
	 * 将图片压缩按本来的长宽比例压缩为100宽度的jpg图片
	 * @param inputStream
	 * @param realPath /surveyLogos目录的真实路径，后面没有斜杠
	 * @return 将生成的文件路径返回 surveyLogos/4198393905112.jpg
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {
		
		OutputStream out = null;
		
		try {
			//1.构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			//2.获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();
			
			//3.计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;
			
			if(sourceWidth > 100) {
				//按比例压缩目标图片的尺寸
				targetWidth = 100;
				targetHeight = sourceHeight/(sourceWidth/100);
				
			}
			
			//4.创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			
			//5.绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
			
			//6.构造目标图片文件名
			String targetFileName = System.nanoTime() + ".jpg";
			
			//7.创建目标图片对应的输出流
			out = new FileOutputStream(realPath+"/"+targetFileName);
			
			//8.获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			
			//9.JPEG编码
			encoder.encode(targetImage);
			
			//10.返回文件名
			return "surveyLogos/"+targetFileName;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
		} finally {
			//10.关闭流
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	/**
	 * MD5加密
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		
		//1.准备工作
		//①检查源字符串是否可用
		if(source == null || source.length() == 0) {
			return null;
		}
		
		//②声明字符数组
		char[] characters = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		
		//③获取MessageDigest对象
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {}
		
		//④获取源字符串的字节数组
		byte[] bytes = source.getBytes();
		
		//⑤声明一个连接字符串的StringBuilder对象
		StringBuilder builder = new StringBuilder();
		
		//2.执行加密
		byte[] targetBytes = digest.digest(bytes);
		
		//3.将加密得到的字节数组转换成对应的字符串
		//①遍历targetBytes
		for (int i = 0; i < targetBytes.length; i++) {
			byte b = targetBytes[i];
			
			//②获取高四位的值
			int highNum = (b >> 4) & 15;
			
			//③获取低四位的值
			int lowNum = b & 15;
			
			//④获取高四位对应的十六进制字符
			char high = characters[highNum];
			
			//⑤获取低四位对应的十六进制字符
			char low = characters[lowNum];
			
			//⑥将得到的字符连接起来
			builder.append(high).append(low);
		}
		
		return builder.toString();
	}

	/**
	 * 从请求参数name值字符串中解析得到Question的id
	 * q23
	 * q24
	 * q25
	 * ……
	 * @param paramName
	 * @return
	 */
	public static Integer parseQuestionId(String paramName) {
		
		String questionIdStr = paramName.substring(1);
		
		return Integer.parseInt(questionIdStr);
	}

	/**
	 * 将请求参数值数组转换为字符串
	 * @param paramValues
	 * @return
	 */
	public static String convertParamValues(String[] paramValues) {
		
		if(paramValues == null || paramValues.length == 0) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < paramValues.length; i++) {
			String param = paramValues[i];
			builder.append(param).append(",");
		}
		
		return builder.substring(0, builder.lastIndexOf(","));
	}

	/**
	 * 将servletPath中附带数据的部分去掉
	 * /guest/survey/toEditUI/param/2/3/abc/5/6
	 * @param servletPath
	 * @return
	 */
	public static String cutServletPath(String servletPath) {
		
		String[] split = servletPath.split("/");
		
		return "/"+split[1]+"/"+split[2]+"/"+split[3];
	}

	/**
	 * 验证权限
	 * @param codeArrStr
	 * @param res
	 * @return
	 */
	public static boolean checkAuthority(String codeArrStr, Res res) {
		
		//1.将codeArrStr还原成int[]，验证是否有效
		int[] codeArr = convertStr2CodeArr(codeArrStr);
		if(codeArr == null || codeArr.length == 0) {
			return false;
		}
		
		//2.从Res对象中获取resPos和resCode
		int resCode = res.getResCode();
		int resPos = res.getResPos();
		
		//3.使用resPos为数组下标到int[]中取值
		int code = codeArr[resPos];
		
		//4.执行与运算
		int result = resCode & code;
		
		//5.检查结果是否为0
		return result != 0;
	}
	
	public static int[] convertStr2CodeArr(String codeArrStr) {
		
		if(codeArrStr == null || codeArrStr.length() == 0) {
			return null;
		}
		
		String[] split = codeArrStr.split(",");
		
		int[] codeArr = new int[split.length];
		
		for (int i = 0; i < split.length; i++) {
			String codeStr = split[i];
			int code = Integer.parseInt(codeStr);
			codeArr[i] = code;
		}
		
		return codeArr;
	}

}
