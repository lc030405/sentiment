package kse.seu.edu.cn.classifier;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class MaxEntClassifier implements IClassify {

	private List<Double> general_weights = new ArrayList<>();
	private Map<Features, Integer> featsCounter = new HashMap<>();
	private Map<Features, Integer> featsId = new HashMap<>();
	private int max_iter = 500;

	public MaxEntClassifier() {
		super();
	}	

	public MaxEntClassifier(String root_path, String filepath) {
		super();
		Corpus corpus = new Corpus(root_path,filepath);
		tarin(corpus.get_train_corpus(3000));
	}

	public List<Double> getGeneral_weights() {
		return general_weights;
	}

	public void setGeneral_weights(List<Double> general_weights) {
		this.general_weights = general_weights;
	}

	public Map<Features, Integer> getFeatsCounter() {
		return featsCounter;
	}

	public void setFeatsCounter(Map<Features, Integer> featsCounter) {
		this.featsCounter = featsCounter;
	}

	public Map<Features, Integer> getFeatsId() {
		return featsId;
	}

	public void setFeatsId(Map<Features, Integer> featsId) {
		this.featsId = featsId;
	}

	public int getMax_iter() {
		return max_iter;
	}

	public void setMax_iter(int max_iter) {
		this.max_iter = max_iter;
	}

	@Override
	public String classify(String sentence) {
		List<String> doc = new JiebaSegmenter().sentenceProcess(sentence);
		ClassPro prob = calculate_probability(doc);
		return prob.getFlag_0() > prob.getFlag_1() ? "0" : "1";
	}

	@Override
	public String classify(File file) {
		return null;
	}

	public void tarin(Traindata train_data) {

        int train_data_length = train_data.getLabels().size();

		for (int i = 0; i < train_data.getInputs().size(); i++) {
			for (String word : train_data.getInputs().get(i)) {
				Features f = new Features(word, train_data.getLabels().get(i));
				if (this.featsCounter.containsKey(f)) {
					this.featsCounter.put(f, this.featsCounter.get(f) + 1);
				} else {
					this.featsCounter.put(f, 1);
				}
			}
		}
		int the_max = getMax(train_data.getInputs());
		int feaNum = this.featsCounter.size();
		List<Double> ep_empirical = new ArrayList<>();
		for (int i = 0; i < feaNum; i++) {
			this.general_weights.add(0.0);
			ep_empirical.add(0.0);
		}

		Iterator<Features> it = featsCounter.keySet().iterator();
		int counter = 0;
		while (it.hasNext()) {
			Features f = it.next();
			ep_empirical.set(counter,this.featsCounter.get(f) * 1.0 / train_data_length);

			this.featsId.put(f, counter);
			counter++;
		}

		for (int i = 0; i < max_iter; i++) {

			// feature expectation on model distribution
			List<Double> ep_model = new ArrayList<>();
			for (int j = 0; j < feaNum; j++) {
				ep_model.add(0.0);
			}

			for (int j = 0; j < train_data.getInputs().size(); j++) {
//				calculate p(y|x)
				ClassPro prob = calculate_probability(train_data.getInputs().get(j));
				if (prob == null) {
					throw new RuntimeException("The program collapse. The iter number:" + i);
				}
				for (String word : train_data.getInputs().get(j)) {
					
					if (featsId.containsKey(new Features(word, 0))) {
						int idx = featsId.get(new Features(word, 0));
						ep_model.set(idx,ep_model.get(idx) + prob.getFlag_0() * (1.0 / train_data_length));
					}
					if (featsId.containsKey(new Features(word, 1))) {
						int idx = featsId.get(new Features(word, 1));
						ep_model.set(idx, ep_model.get(idx) + prob.getFlag_1() * (1.0 / train_data_length));
					}
				}
			}

			List<Double> last_weight = deepclone(general_weights);

			for (int j = 0; j < general_weights.size(); j++) {
				double delta = 1.0 / the_max * Math.log( ep_empirical.get(j) / ep_model.get(j) );
				general_weights.set(j, general_weights.get(j) + delta);
			}

			if (convergence(last_weight)) {
				System.out.println("The program convergence. The iter number:" + (i + 1));
				break;
			}
		}
		
		System.out.println();

	}

	private boolean convergence(List<Double> last_weight) {
		for (int i = 0; i < last_weight.size(); i++) {
			if (Math.abs(last_weight.get(i) - this.general_weights.get(i)) >= 0.001) {
				return false;
			}
		}
		return true;
	}

	private int getMax(List<List<String>> inputs) {
		int res = 0;
		for (List<String> elem : inputs) {
			if (elem.size() > res) {
				res = elem.size();
			}
		}
		return res-1;
	}

	private List<Double> deepclone(List<Double> weights) {
		List<Double> res = new ArrayList<>();
		for (Double ele : weights) {
			res.add(ele);
		}
		return res;
	}

	private ClassPro calculate_probability(List<String> doc) {
		double weight_0 = prob_weight(doc, 0);
		double weight_1 = prob_weight(doc, 1);
		if(Double.isInfinite(weight_0)||Double.isInfinite(weight_1)){
			return null;
		}
		double z = weight_0 + weight_1;
		if(z==0)
			return null;
		if(Double.isInfinite(weight_0)){
			System.out.println();
		}
		if(Double.isInfinite(weight_1)){
			System.out.println();
		}
		try {
			return new ClassPro(weight_0 / z, weight_1 / z);
		} catch (Exception e) {
			return null;
		}
	}

	private double prob_weight(List<String> doc, int label) {
		double weight = 0.0;
		for (String word : doc) {
			Features f = new Features(word.trim(), label);
			if (this.featsId.containsKey(f)) {
				weight += this.general_weights.get(this.featsId.get(f));
			}
		}
		if(Double.isInfinite(Math.exp(weight))){
			System.out.println(weight);
			System.out.println();
		}
		return Math.exp(weight);
	}
}
