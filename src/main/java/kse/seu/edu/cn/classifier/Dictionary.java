package kse.seu.edu.cn.classifier;

import java.io.Serializable;

public class Dictionary implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String key = "";
	private double value=0;
	private int position = 0;
	
	public Dictionary() {
		super();
	}

	public Dictionary(String key, double value, int position) {
		super();
		this.key = key;
		this.value = value;
		this.position = position;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
