import java.io.*;
import java.util.*;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SimpleLogistic;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.classifiers.Evaluation;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

class SimpleLog {
	Instances training;
	Instances validation;
	Instances test;
	SimpleLogistic m_logistic;

	public SimpleLog(String trainingFileName, String valFileName, String testFileName) throws Exception{
		BufferedReader bf = new BufferedReader(new FileReader(trainingFileName));
		BufferedReader bf2 = new BufferedReader(new FileReader(valFileName));
		BufferedReader bf3 = new BufferedReader(new FileReader(testFileName));
		ArffReader arff = new ArffReader(bf);
		ArffReader arff2 = new ArffReader(bf2);
		ArffReader arff3 = new ArffReader(bf3);
		Instances train = arff.getData();
		Instances val = arff2.getData();
		Instances test_temp = arff3.getData();
		StringToWordVector stovec = new StringToWordVector();
		stovec.setInputFormat(train);
		this.m_logistic = new SimpleLogistic();
		this.training = Filter.useFilter(train, stovec);
		this.validation = Filter.useFilter(val, stovec);
		this.test = Filter.useFilter(test_temp, stovec);
	}


	public void buildAndRunClassifier() throws Exception{
		this.training.setClassIndex(0);
		this.test.setClassIndex(0);
		this.validation.setClassIndex(0);
		this.m_logistic.buildClassifier(this.training);


		Evaluation eval = new Evaluation(this.training);
		System.out.println("Cross validation on test set, 10 folds");
		eval.crossValidateModel(this.m_logistic, this.test, 10, new Random(1));
		System.out.println("intermediate F-Measure for cross validation: "+ eval.fMeasure(1));
		System.out.println("Evaluating on validation set");
		eval.evaluateModel(this.m_logistic, this.validation);
		System.out.println(eval.toSummaryString());
		System.out.println("F-measure: "+eval.fMeasure(1));
	}
	public static void main(String[] args) throws Exception {
		SimpleLog log = new SimpleLog("./training.arff", "./validation.arff", "./test.arff");
		log.buildAndRunClassifier();
	}
}