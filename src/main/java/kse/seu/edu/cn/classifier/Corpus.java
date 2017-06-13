package kse.seu.edu.cn.classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kse.seu.edu.cn.tools.FileUtils;

public class Corpus implements Serializable {

	private static final long serialVersionUID = 1L;
	private String root_path = "";
	private String filepath = "";

	private List<List<String>> pos_doc_list = new ArrayList<>();

	private List<List<String>> neg_doc_list = new ArrayList<>();
	
	public Corpus() {
		super();
	}

	public Corpus(String root_path, String filepath) {
		super();
		this.root_path = root_path;
		this.filepath = filepath;
		List<String> contents = FileUtils.readLines(root_path + filepath);

		for (String content : contents) {
			String[] fields = content.substring(4).split("\\s+");
			for (int i = 0; i < fields.length; i++) {
				fields[i] = fields[i].trim();
			}
			List<String> ele = Arrays.asList(fields);
			if(content.startsWith("pos")){
				pos_doc_list.add(ele);
			} else if (content.startsWith("neg")){
				neg_doc_list.add(ele);
			}
		}
	}

	public String getRoot_path() {
		return root_path;
	}

	public void setRoot_path(String root_path) {
		this.root_path = root_path;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public List<List<String>> getPos_doc_list() {
		return pos_doc_list;
	}

	public void setPos_doc_list(List<List<String>> pos_doc_list) {
		this.pos_doc_list = pos_doc_list;
	}

	public List<List<String>> getNeg_doc_list() {
		return neg_doc_list;
	}

	public void setNeg_doc_list(List<List<String>> neg_doc_list) {
		this.neg_doc_list = neg_doc_list;
	}

	public Traindata get_train_corpus(int train_num){
		Traindata train = new Traindata();
		if(train_num < 0){
			train_num = pos_doc_list.size();
		}
		
		for (int i = 0; i < train_num; i++) {
			train.getInputs().add(pos_doc_list.get(i));
			train.getLabels().add(1);
			train.getInputs().add(neg_doc_list.get(i));	
			train.getLabels().add(0);
		}
		return train;
	}
	
	public Traindata get_test_corpus(int start, int end){
		Traindata train = new Traindata();
				
		for (int i = start; i < end; i++) {
			train.getInputs().add(pos_doc_list.get(i));
			train.getLabels().add(1);
			train.getInputs().add(neg_doc_list.get(i));	
			train.getLabels().add(0);
		}
		return train;
	}
	
	public static void main(String[] args) {
		Corpus corpus = new Corpus("f_corpus/", "en_movie_corpus.txt");
		System.out.println(corpus.getNeg_doc_list().size());
		System.out.println(corpus.getNeg_doc_list().size());
	}
	
}
