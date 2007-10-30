package ru.ixxo.crux.common;

import java.util.EventObject;

public class EngineEvent extends EventObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4247631710824811197L;
	
	private String type;	
	private Object msg;
	
	public EngineEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public Object getMessage() {
		return msg;
	}

	public void setMessage(Object msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
