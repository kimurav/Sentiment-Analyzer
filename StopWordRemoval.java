
import java.io.*;
import java.util.*;

class StopWordRemoval {

	public static Boolean isInList(String word, ArrayList<String> list) {
		for(String s: list) {
			if(s.equals(word)) {
				return true;
			}
		}
		return false;
	} 

	public static void main(String args[]) throws IOException {
		BufferedReader featureFile = new BufferedReader(new FileReader("./features.txt"));
		BufferedReader stopWordsFile = new BufferedReader(new FileReader("./stopwords.txt"));
		FileWriter fwriter = new FileWriter("./stop_word_feat.txt");
		ArrayList<String> features = new ArrayList<String>();
		ArrayList<String> stopwords = new ArrayList<String>();

		String line;
		while( (line = featureFile.readLine()) != null ) {
			features.add(line);
		}
		while((line = stopWordsFile.readLine()) != null) {
			stopwords.add(line);
		}

		for(String feat: features) {
			if(!isInList(feat, stopwords)) {
				fwriter.write(feat+"\n");
			}
		}
		fwriter.close();
	}
}