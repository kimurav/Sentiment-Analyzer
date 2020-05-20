
import java.io.*;
import java.util.*;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ArffLoader.ArffReader;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Resample;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;
class SplitDataset {


	/*
	Reandomize the inputfile and remove percent from the input file and output it to the ouput file. 

	*/
	protected static void filterCorpusTestTraining(String inputFileName, String outputFileName, double percent) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(inputFileName));
		ArffReader arffReader = new ArffReader(br);
		Instances corpus = arffReader.getData();
		corpus.setClassIndex(corpus.numAttributes() - 1);
		
		Randomize rand = new Randomize();
		rand.setInputFormat(corpus);
		Instances randomizedCorpus = Filter.useFilter(corpus, rand);


		Resample remove = new Resample();
		remove.setSampleSizePercent(percent);
		remove.setInputFormat(corpus);

		Instances trainingData = Filter.useFilter(corpus, remove);
		
		FileWriter wr = new FileWriter(new File(outputFileName));
		wr.write(trainingData.toString());
		wr.close();

	}

	/*
	Will take the differences of data from inputFile1, and inputFile2 and output it to outputFile2, 
	*/
	protected static void filterCorpusForTest(String inputFile1, String inputFile2, String outputFile) throws Exception {
		BufferedReader corpusReader = new BufferedReader(new FileReader(inputFile1)); // corpus.arff
		BufferedReader trainingReader = new BufferedReader(new FileReader(inputFile2)); // training.arff

		Set<String> corpusSet = new HashSet<String>();
		Set<String> trainingSet = new HashSet<String>();
		Set<String> testSet = new HashSet<String>();

		//Populate the training and corpus lists:
		String line;
		while( (line = corpusReader.readLine()) != null ) {
			if(line.contains("@data") || line.contains("@relation") || line.contains("@attribute") || (line.isEmpty())) {
				continue;
			} else {
				corpusSet.add(line);
			}
		}

		while( (line = trainingReader.readLine()) != null ) {
			if(line.contains("@data") || line.contains("@relation") || line.contains("@attribute") || (line.isEmpty())) {
				continue;
			} else {
				trainingSet.add(line);
			}
		}

		String arffHeader ="@relation _Users_manavpatel_term_project_4500_filtered_sentoken\n@attribute text string\n@attribute @@class@@ {neg,pos}\n@data\n";


		for(String s: corpusSet) {
			testSet.add(s);
		}

		testSet.removeAll(trainingSet);

		FileWriter wr = new FileWriter(new File(outputFile));
		wr.write(arffHeader);
		for(String review: testSet) {
			wr.write(review + "\n");
		}
		wr.close();
		// System.out.println("Corpus List size: " + corpusSet.size() + " Training list size: " + trainingSet.size() + "test set size: " + testSet.size());

	}
	// Read in the training set and treat it as the corpus. Use the remove percentage object to remove 20% of the training set
	public static void splitTrainingIntoValidation(String inputFile, String outputFile) throws Exception {
		String interFile = "intermediate_"+outputFile+".arff"; 
		filterCorpusTestTraining(inputFile, interFile, 20.0);
		filterCorpusForTest(inputFile, interFile, outputFile);

	}

	public static void main(String args[]) throws Exception{
		//filterCorpusTestTraining("./corpus.arff", "./intermediate_training.arff", 15.0);
		filterCorpusTestTraining("./corpus.arff", "./intermediate_training.arff", 85.0);
		filterCorpusForTest("./corpus.arff", "./intermediate_training.arff", "test.arff");
		filterCorpusTestTraining("./intermediate_training.arff", "training.arff", 80);
		filterCorpusForTest("intermediate_training.arff", "training.arff", "validation.arff");
	}
}