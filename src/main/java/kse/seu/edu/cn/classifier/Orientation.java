package kse.seu.edu.cn.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Orientation implements Serializable{

	private static final long serialVersionUID = 1L;
	private String key="";
	private List<Dictionary> adverbs = new ArrayList<>();
	private List<Dictionary> denials = new ArrayList<>();
	private double value = 0;

	public Orientation() {
		super();
	}
	
	public Orientation(String key, double value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<Dictionary> getAdverbs() {
		return adverbs;
	}
	public void setAdverbs(List<Dictionary> adverbs) {
		this.adverbs = adverbs;
	}
	public List<Dictionary> getDenials() {
		return denials;
	}
	public void setDenials(List<Dictionary> denials) {
		this.denials = denials;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
