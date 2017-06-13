package kse.seu.edu.cn.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subclause implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Orientation> positives = new ArrayList<>(); 
	private List<Orientation> negatives = new ArrayList<>(); 
	private List<Orientation> conjunctions = new ArrayList<>(); 
	private List<Orientation> punctuations = new ArrayList<>(); 
	private List<Orientation> patterns = new ArrayList<>();
	private double score=0;
	
	public Subclause() {
		super();
	}

	public List<Orientation> getPositives() {
		return positives;
	}

	public void setPositives(List<Orientation> positives) {
		this.positives = positives;
	}

	public List<Orientation> getNegatives() {
		return negatives;
	}

	public void setNegatives(List<Orientation> negatives) {
		this.negatives = negatives;
	}

	public List<Orientation> getConjunctions() {
		return conjunctions;
	}

	public void setConjunctions(List<Orientation> conjunctions) {
		this.conjunctions = conjunctions;
	}

	public List<Orientation> getPunctuations() {
		return punctuations;
	}

	public void setPunctuations(List<Orientation> punctuations) {
		this.punctuations = punctuations;
	}

	public List<Orientation> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<Orientation> patterns) {
		this.patterns = patterns;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
