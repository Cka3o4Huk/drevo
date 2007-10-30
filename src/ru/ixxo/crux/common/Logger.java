package ru.ixxo.crux.common;

import java.sql.Time;


public class Logger {
	
	public static boolean debug = false;
	public static boolean info = true;
	
	public static void log(String str){
		if(debug){	
			Throwable state = new Throwable();
			long millisec = System.currentTimeMillis();
			Time time = new Time(millisec);		
			System.out.println("[" + time.toString() + "] " 
					+ str + " ["+Thread.currentThread().getName() + "] ("
					+getClassInformation(state)+")");
		}
	}

	public static void info(String str){
		if(debug || info){	
			Throwable state = new Throwable();
			long millisec = System.currentTimeMillis();
			Time time = new Time(millisec);		
			System.out.println("[" + time.toString() + "] " 
					+ str + " ["+Thread.currentThread().getName() + "] ("
					+getClassInformation(state)+")");
		}
	}
	
	private static String getClassInformation(Throwable state){
		StackTraceElement elements[] = state.getStackTrace();
		int size = elements.length;
		if(size == 1){
			return getClassInformation(elements[0]); 		
		}else if(size > 1)
			return getClassInformation(elements[1]);
		return "";
	}

	private static String getClassInformation(StackTraceElement element){
		return element.getClassName() + "."+ element.getMethodName() + "():"+element.getLineNumber();
	}

}

