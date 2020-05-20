import java.io.*;
import java.util.*;
import weka.core.converters.TextDirectoryLoader;
import weka.core.Instances;

public class CorpusFilter {
	HashSet<String> featureSet;

	public CorpusFilter() {
		this.featureSet = new HashSet<String>();
	}

	public void filterFiles(File inputFolder) {
		File[] fileNames = inputFolder.listFiles();
	    for(File file : fileNames) {
	        // if directory call the same method again
	        if(file.isDirectory()){
	            filterFiles(inputFolder);
	        }else{
	            try {
	                readContentAndOutput(file);
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	      }
	    }
	}

	public void readContentAndOutput(File inputFile) throws IOException {
		String folder = inputFile.getParent();
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		if(folder.contains("pos")) {
			String line;
			FileWriter wr = new FileWriter(new File("./filtered_sentoken/pos/"+inputFile.getName()));
			while( (line = reader.readLine()) != null ) {
				String[] tokens = line.split("[ ]+");
				for(int i = 0; i < tokens.length; i++) {
					if(this.featureSet.contains(tokens[i])){
						if(i == (tokens.length - 1)) {
							wr.write(tokens[i] + "\n");
						} else {
							wr.write(tokens[i] + " ");
						}
					}
				}
				wr.write("\n");
			}	
			wr.close();
		} 
		if(folder.contains("neg")) {
			String line;
			FileWriter wr = new FileWriter(new File("./filtered_sentoken/neg/"+inputFile.getName()));
			while( (line = reader.readLine()) != null ) {
				String[] tokens = line.split("[ ]+");
				for(int i = 0; i < tokens.length; i++) {
					if(this.featureSet.contains(tokens[i])){
						if(i == (tokens.length - 1)) {
							wr.write(tokens[i] + "\n");
						} else {
							wr.write(tokens[i] + " ");
						}
					}
				}
			}	
			wr.close();
		}
		
	}

	public void createFeatureSet(File featureFile) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(featureFile))) {
			String feat;
			while( (feat = reader.readLine()) != null ) {
				this.featureSet.add(feat);
			} 
		}
	}


	public static void main(String[] args) throws IOException {
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