package ru.ixxo.crux.engine;

import org.jdom.*;

public interface Task {
	
	public void init(Element arguments);
	
	public Element execute(Element arguments);
	public Element execute();
		
}
