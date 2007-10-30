package ru.ixxo.crux.engine.core;

import java.lang.reflect.Method;

import ru.ixxo.crux.engine.Task;

public class Executor {
	
	public void executeTask(Task task, Method method){
		
		task.execute();				
	}
}
