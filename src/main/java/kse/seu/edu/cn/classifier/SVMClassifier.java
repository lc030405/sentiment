package kse.seu.edu.cn.classifier;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class SVMClassifier implements IClassify {

	public SVMClassifier() {
		super();
	}

	public SVMClassifier(String root_path, String filepath) {
		super();
		Corpus corpus = new Corpus(root_path, filepath);
		tarin(corpus.get_train_corpus(3000));
	}

	private void tarin(Traindata get_train_corpus) {
		// TODO Auto-generated method stub

	}

	@Override
	public String classify(String sentence) {

		return null;
	}

	@Override
	public String classify(File file) {
		return null;
	}

	public static void main(String[] args) {
		try {
			Classifier m_classifier = new LibSVM();
			File inputFile = new File("C:/Program Files/Weka-3-8/data/cpu.with.vendor.arff");// 训练语料文件
			ArffLoader atf = new ArffLoader();

			atf.setFile(inputFile);

			Instances instancesTrain = atf.getDataSet(); // 读入训练文件
			inputFile = new File("C:/Program Files/Weka-3-8/data/cpu.with.vendor.arff");// 测试语料文件
			atf.setFile(inputFile);
			Instances instancesTest = atf.getDataSet(); // 读入测试文件
			instancesTest.setClassIndex(0); // 设置分类属性所在行号（第一行为0号），instancesTest.numAttributes()可以取得属性总数
			double sum = instancesTest.numInstances(), // 测试语料实例数
					right = 0.0f;
			instancesTrain.setClassIndex(0);

			m_classifier.buildClassifier(instancesTrain); // 训练
			for (int i = 0; i < sum; i++){// 测试分类结果
				if (m_classifier.classifyInstance(instancesTest.instance(i)) == instancesTest.instance(i).classValue()){// 如果预测值和答案值相等（测试语料中的分类列提供的须为正确答案，结果才有意义）
					right++;// 正确值加1
				}
			}

			System.out.println("SVM classification precision:" + (right / sum));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
