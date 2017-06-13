package kse.seu.edu.cn.classifier;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class BayesClassifier implements IClassify, Serializable {

	private static final long serialVersionUID = 1L;

	private double pos_p = 0;
	private double neg_p = 0;
	private Map<String, Double> pos_word_p = new HashMap<>();
	private Map<String, Double> neg_word_p = new HashMap<>();
	private Set<String> total_word = new HashSet<>();

	public BayesClassifier(String root_path, String filepath) {
		super();
		Corpus corpus = new Corpus(root_path,filepath);
		train(corpus.get_train_corpus(-1));
	}
	
	public BayesClassifier() {
		super();
	}

	public double getPos_p() {
		return pos_p;
	}

	public void setPos_p(double pos_p) {
		this.pos_p = pos_p;
	}

	public double getNeg_p() {
		return neg_p;
	}

	public void setNeg_p(double neg_p) {
		this.neg_p = neg_p;
	}

	public Map<String, Double> getPos_word_p() {
		return pos_word_p;
	}

	public void setPos_word_p(Map<String, Double> pos_word_p) {
		this.pos_word_p = pos_word_p;
	}

	public Map<String, Double> getNeg_word_p() {
		return neg_word_p;
	}

	public void setNeg_word_p(Map<String, Double> neg_word_p) {
		this.neg_word_p = neg_word_p;
	}

	public Set<String> getTotal_word() {
		return total_word;
	}

	public void setTotal_word(Set<String> total_word) {
		this.total_word = total_word;
	}

	@Override
	public String classify(String sentence) {
		List<String> words = new JiebaSegmenter().sentenceProcess(sentence);
		double pos_score = 0;
		double neg_score = 0;
		for (String word : words) {
			if (pos_word_p.containsKey(word.trim()))
				pos_score += pos_word_p.get(word.trim());
		}
		pos_score += Math.log(pos_p);

		for (String word : words) {
			if (neg_word_p.containsKey(word.trim()))
				neg_score += neg_word_p.get(word.trim());
		}
		neg_score += Math.log(neg_p);
		System.out.println(pos_score+":"+neg_score);
		return pos_score > neg_score ? "1" : "0";
	}

	@Override
	public String classify(File file) {
		return null;
	}

	public void train(Traindata train_data) {
		Map<String, Integer> total_pos_data = new HashMap<>();
		Map<String, Integer> total_neg_data = new HashMap<>();

		int total_pos_length = 0;
		int total_neg_length = 0;
		for (int i = 0; i < train_data.getInputs().size(); i++) {
			List<String> elems = train_data.getInputs().get(i);
			int flag = train_data.getLabels().get(i);
			if (flag == 1) {
				for (String elem : elems) {
					total_pos_length++;
					if (total_pos_data.containsKey(elem.trim())) {
						total_pos_data.put(elem.trim(), total_pos_data.get(elem.trim()) + 1);
					} else {
						total_pos_data.put(elem.trim(), 1);
						total_word.add(elem.trim());
					}
				}
			} else if (flag == 0) {
				for (String elem : elems) {
					total_neg_length++;
					if (total_neg_data.containsKey(elem.trim())) {
						total_neg_data.put(elem.trim(), total_neg_data.get(elem.trim()) + 1);
					} else {
						total_neg_data.put(elem.trim(), 1);
						total_word.add(elem.trim());
					}
				}
			}
		}

		this.pos_p = total_pos_length * 1.0 / (total_pos_length + total_neg_length);
		this.neg_p = total_neg_length * 1.0 / (total_pos_length + total_neg_length);

		for (String word : total_word) {
			double poss = 0;

			if (!total_pos_data.containsKey(word)) {
				poss = 1e-100;
			} else {
				poss = total_pos_data.get(word);
			}
			pos_word_p.put(word, Math.log(poss / total_pos_length));

			if (!total_neg_data.containsKey(word)) {
				poss = 1e-100;
			} else {
				poss = total_neg_data.get(word);
			}
			neg_word_p.put(word, Math.log(poss / total_neg_length));

			System.out.println("BayesClassifier trains over!");
		}
	}
}
