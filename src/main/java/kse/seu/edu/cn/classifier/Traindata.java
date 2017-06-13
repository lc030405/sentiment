package kse.seu.edu.cn.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Traindata implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<List<String>> inputs = new ArrayList<>();
	private List<Integer> labels = new ArrayList<>();
	
	public Traindata() {
		super();
	}

	public Traindata(List<List<String>> inputs, List<Integer> labels) {
		super();
		this.inputs = inputs;
		this.labels = labels;
	}

	public List<List<String>> getInputs() {
		return inputs;
	}

	public void setInputs(List<List<String>> inputs) {
		this.inputs = inputs;
	}

	public List<Integer> getLabels() {
		return labels;
	}

	public void setLabels(List<Integer> labels) {
		this.labels = labels;
	}

}
