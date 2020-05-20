import java.io.*;
import weka.core.converters.TextDirectoryLoader;
import weka.core.Instances;


class FilterSplitCorpus {
	public static void main(String args[]) throws IOException {
		File features = new File(args[0]);
		File pos = new File("./txt_sentoken/pos");
		File neg = new File("./txt_sentoken/neg");

		CorpusFilter cf = new CorpusFilter();
		cf.createFeatureSet(features);
		cf.filterFiles(pos);
		cf.filterFiles(neg);
		System.out.println("Number of features: " + cf.featureSet.size());
		TextDirectoryLoader loader = new TextDirectoryLoader();
		File corpusFolder = new File("./filtered_sentoken");
		loader.setDirectory(corpusFolder);
		Instances corpus = loader.getDataSet();
		FileWriter outputFile = new FileWriter(new File("./corpus.arff"));
		outputFile.write(corpus.toString());
		outputFile.close();

	}
}