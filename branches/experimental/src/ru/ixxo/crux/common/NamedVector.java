package ru.ixxo.crux.common;

import java.util.Vector;

public class NamedVector extends Vector {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

	public NamedVector(String name) {
		this.name = name;
	}

	public NamedVector(String name, Object elements[]) {
		this.name = name;
		for (int i = 0, n = elements.length; i < n; i++) {
			add(elements[i]);
		}
	}

	public String toString() {
		return "[" + name + "]";
	}
}