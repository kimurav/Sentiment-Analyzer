import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

class FeatureSelection {

  POSTaggerME posTagger;
  ArrayList<Document> documents;
  TreeMap<String, Integer> map;
  ArrayList<String> features;

  public FeatureSelection(POSModel model ) {
    this.posTagger = new POSTaggerME(model);
    this.documents = new ArrayList<Document>();
    this.map = new TreeMap<String,Integer>();
    this.features = new ArrayList<String>();
  }

  public void tagFiles(File folder) {
    File[] fileNames = folder.listFiles();
    for(File file : fileNames) {
        // if directory call the same method again
        if(file.isDirectory()){
            tagFiles(file);
        }else{
            try {
                readContent(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
      }
    }
  }

  public void readContent(File filename) throws IOException{
      // System.out.println("read file " + file.getCanonicalPath() );
      try(BufferedReader br  = new BufferedReader(new FileReader(filename))){
        String strLine;
        String tokens[];
        Document doc = new Document();
        while((strLine = br.readLine()) != null) {
          tokens = strLine.split("[ ]+");
          for(int i = 0; i < tokens.length; i++) {
            doc.addWord(tokens[i]);
          }
          doc.addTag(this.posTagger.tag(tokens));
        }

        this.documents.add(doc);
      }
  }


  public void filterTags() {
    for(Document d: this.documents) {
      int i = 0;
      for(String tag: d.taggedWords) {
        switch(tag){
          case "JJ":
          case "RB":
            if(this.map.containsKey(d.words.get(i))) {
              int occurences = this.map.get(d.words.get(i));
              this.map.replace(d.words.get(i), occurences+1);
            } else {
               this.map.put(d.words.get(i), 1);
            }
            break;
        }
        i++;
      }
    }
  }

  public void filterTagsAndStopWords() throws IOException {
    ArrayList<String> stopWords = new ArrayList<String>();
    BufferedReader br = new BufferedReader(new FileReader("./stopwords.txt"));
    String line;
    while( (line = br.readLine()) != null) {
      stopWords.add(line);
    }
    for(Document d: this.documents) {
      int i = 0; 
      for(String tag: d.taggedWords) {
        Boolean isStopWord = false;
        for(String sw: stopWords) {
          if(sw.equals(d.words.get(i))) {
            isStopWord = true;
          }
        }
        switch(tag) {
          case "JJ":
          case "RB":
            if(isStopWord) {
              break;
            } else if(this.map.containsKey(d.words.get(i))) {
              int occurences = this.map.get(d.words.get(i));
              this.map.replace(d.words.get(i), occurences+1);
            } else {
               this.map.put(d.words.get(i), 1);
            }
            break;
        }
        i++;
      }
    }
  }

  public void getDifferentSizes() {
    int tot = 0;
    for(Document d: this.documents) {
      if(d.words.size() != d.taggedWords.size()) {
        System.out.println("different");
        tot++;
      }
    }
    System.out.println("Total differences " + tot);
  }

  static <K,V extends Comparable<? super V>>
  SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
      SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
          new Comparator<Map.Entry<K,V>>() {
              @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                  int res = e1.getValue().compareTo(e2.getValue());
                  return res != 0 ? res : 1;
              }
          }
      );
      sortedEntries.addAll(map.entrySet());
      return sortedEntries;
  }

  public void createFeatureList(SortedSet s) {
    Iterator i = s.iterator();
    while(i.hasNext()){
      Map.Entry e = (Map.Entry) i.next();
      String feature = (String)e.getKey();
      this.features.add(feature);
    }
  }

  public static void main(String[] args) throws IOException {
    int upperBound = Integer.parseInt(args[0]);
    int featureSize = Integer.parseInt(args[1]);
    File neg = new File("./txt_sentoken/neg");
    File pos = new File("./txt_sentoken/pos");
    File outputFile = new File("./features.txt");
    FileWriter wr = new FileWriter(outputFile);
    InputStream posTagStream = new FileInputStream("./OpenNLP_models/en-pos-maxent.bin");
    POSModel posModel = new POSModel(posTagStream);
    FeatureSelection fs = new FeatureSelection(posModel);
    System.out.println("Tagging the reviews...");
    fs.tagFiles(neg);
    fs.tagFiles(pos);
    System.out.println("Finished tagging the input...\nFiltering tag for adjectives and adverbs...");
    fs.filterTags();  
    System.out.println("Size of map: " + fs.map.size());
    System.out.println("Size of documents: " + fs.documents.size());
    SortedSet set = entriesSortedByValues(fs.map);
    fs.createFeatureList(set);
    int totalWords = fs.features.size();
    if((upperBound == 0 ) && (featureSize == 0)) {
       for(int j = fs.features.size()-1; j >= 0 ; j--) {
      String feat = fs.features.get(j);
      wr.write(feat + "\n");
    }
    wr.close();
  } else {
      for(int j = fs.features.size()-upperBound; j >= (totalWords-(featureSize+upperBound)) ; j--) {
        String feat = fs.features.get(j);
        wr.write(feat + "\n");
      }
      wr.close();
    }
  }
}


class Document {
  ArrayList<String> words;
  ArrayList<String> taggedWords;

  public Document() {
    this.words = new ArrayList<String>();
    this.taggedWords = new ArrayList<String>();
  }

  public void addWord(String s) {
    this.words.add(s);
  }

  public void addTag(String[] tags) {
    for(int i = 0; i < tags.length; i++) {
      this.taggedWords.add(tags[i]);
    }
  }
}
