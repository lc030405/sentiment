package kse.seu.edu.cn.classifier;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;

import kse.seu.edu.cn.tools.InstanceFileTools;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class SVMClassifier implements IClassify, Serializable {

	private static final long serialVersionUID = 1L;

	private double[][] vectors = null;
	private List<String> total_words = new ArrayList<>();
	private Traindata train_data = null;
	private Classifier classifier = null;

	public SVMClassifier() {
		super();
	}

	public SVMClassifier(String root_path, String filepath) {
		super();
		Corpus corpus = new Corpus(root_path, filepath);
		this.classifier =  tarin(corpus.get_train_corpus(3000));
	}

	private Classifier tarin(Traindata input_data) {
		this.train_data = input_data;
		get_total_words(train_data);
		this.vectors = new double[train_data.getLabels().size()][total_words.size()];
		getVectors(train_data);
		try {
			Instances insTrain = InstanceFileTools.generatePopularInstance(this.vectors, input_data.getLabels());
//			InstanceFileTools.generateArffFile(insTrain,"train.arff");
			Classifier classifier = (Classifier) Class.forName("weka.classifiers.functions.LibSVM").newInstance();
			String[] optSVM = weka.core.Utils.splitOptions("-c 8.0 -g 8.0");
			classifier.setOptions(optSVM);
			classifier.buildClassifier(insTrain);
			return classifier;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getVectors(Traindata train_data) {

		for (int i = 0; i < vectors.length; i++) {
			for (int j = 0; j < vectors[i].length; j++) {
				// 归一化处理
				if (train_data.getInputs().get(i).size() > 0)
					vectors[i][j] = getWordCountInDoc(train_data.getInputs().get(i), j) * 1.0
							/ train_data.getInputs().get(i).size();
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
		
//		Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
//		dataUnlabeled.add(newInst);
//		dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);        
//		double classif = ibk.classifyInstance(dataUnlabeled.firstInstance());
		
		if(this.classifier!=null){
			List<String> words = new JiebaSegmenter().sentenceProcess(sentence);
			double[] input = doc2vector(words);
			
			FastVector attributes = new FastVector();
			for (int i = 0; i < vectors[0].length; i++) {
				attributes.addElement(new Attribute("Att_" + i));
			}
			attributes.addElement(new Attribute("label"));
			
			Instances dataUnlabeled = new Instances("TestInstances", attributes, 0);

			Instance newInst = new Instance(input.length+1);
			for (int i = 0; i < input.length; i++) {
				newInst.setValue(i, input[i]);
			}
			newInst.setValue(input.length, "0");
			
			dataUnlabeled.add(newInst);
			
			dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1); 
			
			try {
				System.out.println(classifier.classifyInstance(dataUnlabeled.firstInstance()));
				return (int)classifier.classifyInstance(dataUnlabeled.firstInstance())+"";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
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
