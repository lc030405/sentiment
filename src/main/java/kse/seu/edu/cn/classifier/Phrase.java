package kse.seu.edu.cn.classifier;

import java.io.Serializable;

public class Phrase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String key="";
	private String value="";
	private String start="";
	private String end = "";
	private String head="";
	private String between_tag = "";

	public Phrase(){
		super();
	}

	public Phrase(String key, String value) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBetween_tag() {
		return between_tag;
	}

	public void setBetween_tag(String between_tag) {
		this.between_tag = between_tag;
	}
}
