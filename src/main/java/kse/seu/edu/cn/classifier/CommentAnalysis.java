package kse.seu.edu.cn.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentAnalysis implements Serializable{

	private static final long serialVersionUID = 1L;

	private double score= 0.0;
	
	private List<Subclause> the_clauses = new ArrayList<>();

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public List<Subclause> getThe_clauses() {
		return the_clauses;
	}

	public void setThe_clauses(List<Subclause> the_clauses) {
		this.the_clauses = the_clauses;
	}

	public CommentAnalysis() {
		super();
	}

	public CommentAnalysis(double score, List<Subclause> the_clauses) {
		super();
		this.score = score;
		this.the_clauses = the_clauses;
	}
}
