package kse.seu.edu.cn.classifier;

import java.io.Serializable;

public class Features implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String word;
	private int label;
	
	public Features(String word, int label) {
		super();
		this.word = word;
		this.label = label;
	}
	public Features() {
		super();
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	@Override
	public boolean equals(Object arg0) {
		Features f = (Features)arg0;
		if(f.word.equals(this.word) && f.label == this.label)
			return true;
		return false;
	}
	@Override
	public int hashCode() {
		return this.word.hashCode()+this.label;
	}

	
}
