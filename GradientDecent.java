
import java.io.*;
import java.util.*;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SGDText;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.classifiers.Evaluation;

class GradientDecent {
	Instances training;
	Instances validation;
	Instances test;
	SGDText m_sgd;


	public GradientDecent(String trainingFileName, String valFileName, String testFileName) throws Exception{
		BufferedReader bf = new BufferedReader(new FileReader(trainingFileName));
		BufferedReader bf2 = new BufferedReader(new FileReader(valFileName));
		BufferedReader bf3 = new BufferedReader(new FileReader(testFileName));
		ArffReader arff = new ArffReader(bf);
		ArffReader arff2 = new ArffReader(bf2);
		ArffReader arff3 = new ArffReader(bf3);
		this.training = arff.getData();
		this.m_sgd = new SGDText();
		this.validation = arff2.getData();	
		this.test = arff3.getData();
	}
	public void buildAndRunSGD() throws Exception {
		this.training.setClassIndex(this.training.numAttributes() -1);
		this.test.setClassIndex(this.test.numAttributes() - 1);
		this.validation.setClassIndex(this.validation.numAttributes() - 1);
		this.m_sgd.buildClassifier(this.training);

		Evaluation eval = new Evaluation(this.training);
		eval.crossValidateModel(this.m_sgd, this.test, 10, new Random(1));
		eval.evaluateModel(this.m_sgd, this.validation);
		System.out.println(eval.toSummaryString());
		System.out.println("F-Measure: " + eval.fMeasure(1));

	}
	public static void main(String args[]) throws Exception {
		GradientDecent gd = new GradientDecent("./training.arff", "./validation.arff", "./test.arff");
		gd.buildAndRunSGD();
	}
}