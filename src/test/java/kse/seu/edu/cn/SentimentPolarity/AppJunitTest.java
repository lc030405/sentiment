package kse.seu.edu.cn.SentimentPolarity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.huaban.analysis.jieba.JiebaSegmenter;

import kse.seu.edu.cn.classifier.BayesClassifier;
import kse.seu.edu.cn.classifier.DictClassifier;
import kse.seu.edu.cn.classifier.KNNClassifier;
import kse.seu.edu.cn.classifier.MaxEntClassifier;
import kse.seu.edu.cn.classifier.SVMClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class AppJunitTest {
	
	@Test
	public void testDemo() {
	    JiebaSegmenter segmenter = new JiebaSegmenter();
	    String[] sentences =
	        new String[] {"你这个人的性格的好坏不由我来评判。", "我不喜欢日本和服。", "雷猴回归人间。",
	                      "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
	    for (String sentence : sentences) {
	    	
	        System.out.println(segmenter.sentenceProcess(sentence));
	    }
	    
	}
	
	@Test
	public void testDictClassifier() {
		DictClassifier dclassifier = new DictClassifier("f_dict/", 
				"phrase_dict.txt", "positive_dict.txt", "negative_dict.txt", 
				"conjunction_dict.txt", "punctuation_dict.txt", 
				"adverb_dict.txt", "denial_dict.txt");

		System.out.println(dclassifier.getPhrase_dict().size());//43
		System.out.println(dclassifier.getPositive_dict().size());//4554
		System.out.println(dclassifier.getNegative_dict().size());//4450
		System.out.println(dclassifier.getConjunction_dict().size());//27
		System.out.println(dclassifier.getPunctuation_dict().size());//22
		
		System.out.println(dclassifier.getAdverb_dict().size());//98
		System.out.println(dclassifier.getDenial_dict().size());//24
	}
	
	public static void main(String[] args) throws Exception {
		
	}
	@Test
	public void testIndex() {
		String line = "向 ... 倾斜	1";
		int index = line.trim().lastIndexOf("	");
		System.out.println(index);
		System.out.println(line.substring(0, index).trim());
		System.out.println(Double.parseDouble(line.substring(index).trim()));
	}
	
	@Test
	public void testList() {
		List<String> list = new ArrayList<>();
		list.add(0, "1");
		list.add(0, "2");
		list.add(0, "3");
		
		System.out.println(list);
	}
	
	@Test
	public void testWeka() throws Exception {
		Classifier m_classifier = new J48(); 
        File inputFile = new File("C://Program Files//Weka-3-8//data//cpu.with.vendor.arff");//训练语料文件
        ArffLoader atf = new ArffLoader(); 
        atf.setFile(inputFile);
        Instances instancesTrain = atf.getDataSet(); // 读入训练文件    
        inputFile = new File("C://Program Files//Weka-3-8//data//cpu.with.vendor.arff");//测试语料文件
        atf.setFile(inputFile);          
        Instances instancesTest = atf.getDataSet(); // 读入测试文件
        instancesTest.setClassIndex(0); //设置分类属性所在行号（第一行为0号），instancesTest.numAttributes()可以取得属性总数
        double sum = instancesTest.numInstances(),//测试语料实例数
        right = 0.0f;
        instancesTrain.setClassIndex(0);
 
        m_classifier.buildClassifier(instancesTrain); //训练            
        for(int  i = 0;i<sum;i++)//测试分类结果
        {
            if(m_classifier.classifyInstance(instancesTest.instance(i))==instancesTest.instance(i).classValue())//如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
            {
              right++;//正确值加1
            }
        }
        System.out.println("J48 classification precision:"+(right/sum));
	}
	
	@Test
	public void testWekaNaveBayes() throws Exception {

	}
	
	@Test
	public void testString() {
		String temp = "123456";
		System.out.println(temp.substring(2));
	}
	
	@Test
	public void testBayes(){
		BayesClassifier bayes = new BayesClassifier("f_corpus/","ch_hotel_corpus.txt");
		System.out.println(bayes.classify("这是个垃圾电影，不好看。"));
	}
	
	@Test
	public void testCopy(){
		List<Integer> a = new ArrayList<>();
		a.add(1);
		a.add(2);
		a.add(3);
		a.add(4);
		
		List<Integer> b = deepclone(a);
		
		b.add(5);
		System.out.println("a:"+a);
		System.out.println("b:"+b);
	}
	
	private List<Integer> deepclone(List<Integer> a) {
		List<Integer> res = new ArrayList<>();
		for (Integer ele : a) {
			res.add(ele);
		}
		return res;
	}
	
	@Test
	public void testMaxEnt(){
		MaxEntClassifier maxent = new MaxEntClassifier("f_corpus/","ch_waimai_corpus.txt");
		System.out.println(maxent.classify("这些食物好吃。"));
	}
	
	@Test
	public void testKNN(){
		KNNClassifier maxent = new KNNClassifier("f_corpus/","ch_waimai_corpus.txt");
		System.out.println(maxent.classify("这个菜太好吃了"));
	}
	
	@Test
	public void testSVM(){
		SVMClassifier svm = new SVMClassifier("f_corpus/","ch_waimai_corpus.txt");
		System.out.println(svm.classify("这个菜太好吃了"));
	}
}
