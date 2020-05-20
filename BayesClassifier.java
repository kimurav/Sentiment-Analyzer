import java.io.*;
import java.util.*;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomialText;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.classifiers.Evaluation;


class BayesClassifier {
	Instances training;
	Instances validation;
	Instances test;
	NaiveBayesMultinomialText m_bayes;

	public BayesClassifier(String trainingFileName, String valFileName, String testFileName) throws Exception{
		BufferedReader bf = new BufferedReader(new FileReader(trainingFileName));
		BufferedReader bf2 = new BufferedReader(new FileReader(valFileName));
		BufferedReader bf3 = new BufferedReader(new FileReader(testFileName));
		ArffReader arff = new ArffReader(bf);
		ArffReader arff2 = new ArffReader(bf2);
		ArffReader arff3 = new ArffReader(bf3);
		this.training = arff.getData();
		this.m_bayes = new NaiveBayesMultinomialText();
		this.validation = arff2.getData();	
		this.test = arff3.getData();
	}


	public void buildAndRunClassifier() throws Exception{
		this.training.setClassIndex(this.training.numAttributes() -1);
		this.test.setClassIndex(this.test.numAttributes() - 1);
		this.validation.setClassIndex(this.validation.numAttributes() - 1);
		this.m_bayes.buildClassifier(this.training);
		//System.out.println(this.m_bayes.toString());

		Evaluation eval = new Evaluation(this.training);
		System.out.println("Cross validation on test set, 10 folds");
		eval.crossValidateModel(this.m_bayes, this.test, 10, new Random(1));
		System.out.println("intermediate F-Measure for cross validation: "+ eval.fMeasure(1));
		System.out.println("Evaluating on validation set");
		eval.evaluateModel(this.m_bayes, this.validation);
		System.out.println(eval.toSummaryString());
		System.out.println("F-measure: "+eval.fMeasure(1));
	}
	public static void main(String[] args) throws Exception {
		BayesClassifier bayes = new BayesClassifier("./training.arff", "./validation.arff", "./test.arff");
		bayes.buildAndRunClassifier();
	}
}