package kse.seu.edu.cn.classifier;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class KNNClassifier implements IClassify {

	private int K = 11;
	private Set<String> stopwords = new HashSet<>();
	private List<String> total_words = new ArrayList<>();
	private Set<String> best_words = new HashSet<>();
	private double[][] vectors = null;
	private Traindata train_data = null;

	public KNNClassifier() {
		super();
	}

	public KNNClassifier(String root_path, String filepath) {
		super();
		Corpus corpus = new Corpus(root_path, filepath);
		train(corpus.get_train_corpus(-1));
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	public Set<String> getStopwords() {
		return stopwords;
	}

	public void setStopwords(Set<String> stopwords) {
		this.stopwords = stopwords;
	}

	public List<String> getTotal_words() {
		return total_words;
	}

	public void setTotal_words(List<String> total_words) {
		this.total_words = total_words;
	}

	public Set<String> getBest_words() {
		return best_words;
	}

	public void setBest_words(Set<String> best_words) {
		this.best_words = best_words;
	}

	public double[][] getVectors() {
		return vectors;
	}

	public void setVectors(double[][] vectors) {
		this.vectors = vectors;
	}

	public Traindata getTrain_data() {
		return train_data;
	}

	public void setTrain_data(Traindata train_data) {
		this.train_data = train_data;
	}

	private void train(Traindata train_data) {
		this.train_data = train_data;
		get_total_words(train_data);
		this.vectors = new double[train_data.getLabels().size()][total_words.size()];
		getVectors(train_data);
	}

	private void getVectors(Traindata train_data) {

		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[i].length; j++) {
				// 归一化处理
				if (train_data.getInputs().get(i).size() > 0)
					vectors[i][j] = getWordCountInDoc(train_data.getInputs().get(i), j)
							* 1.0 / train_data.getInputs().get(i).size();
			}
		}
	}

	private int getWordCountInDoc(List<String> words, int index) {
		int counter = 0;
		for (String word : words) {
			if (word.equals(total_words.get(index))) {
				counter++;
			}
		}
		return counter;
	}

	private void get_total_words(Traindata train_data) {
		Set<String> words = new HashSet<>();
		for (List<String> elems : train_data.getInputs()) {
			for (String elem : elems) {
				words.add(elem.trim());
			}
		}
		for (String word : words) {
			this.total_words.add(word);
		}
	}

	@Override
	public String classify(String sentence) {
		List<String> words = new JiebaSegmenter().sentenceProcess(sentence);
		double[] input = doc2vector(words);
		int[] sorted_distances = get_sorted_distances(input);
		System.out.println();
		System.out.print("The K = "+K+" sample is: ");
		for (int i = 0; i < sorted_distances.length; i++) {
			System.out.print(" "+sorted_distances[i]);
		}
		
		int pos_count = 0;
		int neg_count = 0;
		for (int i = 0; i < sorted_distances.length; i++) {
			if (train_data.getLabels().get(sorted_distances[i]) == 0) {
				neg_count++;
			} else {
				pos_count++;
			}
		}
		System.out.println("\n"+pos_count+":"+neg_count);
		return pos_count > neg_count ? "1" : "0";
	}

	private int[] get_sorted_distances(double[] input) {
		double[] results = new double[K];
		int[] indeies = new int[K];
		for (int i = 0; i < results.length; i++) {
			results[i] = Double.MAX_VALUE;
		}

		for (int i = 0; i < vectors.length; i++) {
			double distance = getSq_Distance(input, vectors[i]);

			int index = -1;
			for (int j = 0; j < results.length; j++) {
				if (distance < results[j]) {
					index = j;
					break;
				}
			}
			
			if(index >= 0){
				for (int j = results.length - 1; j > index; j--) {
					results[j] = results[j - 1];
				}
				results[index] = distance;

				for (int j = results.length - 1; j > index; j--) {
					indeies[j] = indeies[j - 1];
				}
				indeies[index] = i;
			}
		}
		System.out.print("The K = "+K+" Distances is: ");
		for (int i = 0; i < results.length; i++) {
			System.out.print(" "+results[i]);
		}
		return indeies;
	}

	private double getSq_Distance(double[] input, double[] ds) {
		double sum = 0.0;
		for (int i = 0; i < ds.length; i++) {
			sum += Math.pow(Math.abs(ds[i] - input[i]), 2);
		}
		sum = Math.sqrt(sum);
		return sum;
	}

	private double[] doc2vector(List<String> words) {
		double[] wordVector = new double[total_words.size()];
		for (int i = 0; i < wordVector.length; i++) {
			if (words.size() > 0)
				wordVector[i] = getWordCountInDoc(words, i)*1.0 / words.size();
		}
		return wordVector;
	}

	@Override
	public String classify(File file) {
		return null;
	}
}
