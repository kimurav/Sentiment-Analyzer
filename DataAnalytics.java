import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

class DataAnalytics {

  ArrayList<Review> reviews;
  int numDocuments;

  int minDocLenghtSent;
  int maxDocLengthSent;
  int avgDocLengthSent;

  int minDocLengthTok;
  int maxDocLengthTok;
  int avgDocLengthTok;

  int avgSentLenght;

  DataAnalytics() {
    this.reviews = new ArrayList<Review>();
  }
// Uses listFiles method
  public void listAllFiles(File folder){
      File[] fileNames = folder.listFiles();
      for(File file : fileNames){
          // if directory call the same method again
          if(file.isDirectory()){
              listAllFiles(file);
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
  // Uses Files.walk method
  public void listAllFiles(String path){
      System.out.println("In listAllfiles(String path) method");
      try(Stream<Path> paths = Files.walk(Paths.get(path))) {
          paths.forEach(filePath -> {
              if (Files.isRegularFile(filePath)) {
                  try {
                      readContent(filePath);
                  } catch (Exception e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }
              }
          });
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }

  public void readContent(File file) throws IOException{
      // System.out.println("read file " + file.getCanonicalPath() );
      try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            ArrayList<String> list = new ArrayList<String>();
            // Read lines from the file, returns null when end of stream
            // is reached
            int i = 0;
            while((strLine = br.readLine()) != null){
              list.add(strLine);
             i++;
            }
            Review r = new Review(list, list.size(), file.getName());
            this.reviews.add(r);
            // System.out.println(file.getName()+ " "+ " length: " + i);
      }
  }

  public int getNumSentences() {
    int total = 0;
    for(Review r: this.reviews) {
      total += r.sents.size();
    }
    return total;
  }

  public int getMinSentences() {
    int min = -1;
    for(Review r: this.reviews) {
      if(min == -1) {
        min = r.sents.size();
      }
      if(r.sents.size() < min) {
        min = r.sents.size();
      }
    }
    return min;
  }

  public double getAvgDocLength() {
    double avg = 0.0;
    double totalSents = (double) getNumSentences();
    avg = (double) (totalSents / this.reviews.size());
    return avg;
  }

  public int getMaxSentences() {
    int max = -1;
    for(Review r: this.reviews) {
      if(max == -1) {
        max = r.sents.size();
      }
      if(r.sents.size() > max) {
        max = r.sents.size();
      }
    }
    return max;
  }

  public int getTotalTokens() {
    int total = 0;
    for(Review r: this.reviews) {
      for(Sentence s: r.sents) {
        total += s.tokens.length;
      }
    }
    return total;
  }

  public int getMinTokens() {
    int min = 1000;
    for(Review r: this.reviews) {
      int docTotal = 0;
      for(Sentence s: r.sents) {
        docTotal += s.tokens.length;
      }
      if(docTotal <= min) {
        min = docTotal;
      }
    }
    return min;
  }

// FIX get all the tokens for every setnence
  public int getMaxTokens() {
    int max = -1;
    for(Review r: this.reviews) {
      int docTotal = 0;
      for(Sentence s: r.sents) {
        docTotal += s.tokens.length;
      }
      if(docTotal > max) {
        max = docTotal;
      }
    }
    return max;
  }

  public double getAvgTokens() {
    double  avg = 0;
    double total = (double)getTotalTokens();
    avg = (double)(total / this.reviews.size());
    return avg;
  }

  public double getAvgTokensPerSentence() {
    double avg = 0;
    int numTokens = getTotalTokens();
    int numSents = getNumSentences();
    avg = (double) (numTokens / numSents);
    return avg;
  }

  public void outputAvgSentLenPerDoc() {
    for(Review r: this.reviews) {
      System.out.print("Doc: " + r.docName);
      double avg = 0;
      int numSents = r.getSentLength();
      int numtokens = r.getTotalTokens();
      avg = (double)(numtokens / numSents);
      System.out.print(" Avg: " + avg +"\n");
    }
  }

  public void readContent(Path filePath) throws IOException{
      System.out.println("read file " + filePath);
      List<String> fileList = Files.readAllLines(filePath);
      System.out.println("" + fileList);
  }
  public static void main(String args[]) {
    File f = new File("./txt_sentoken");
    DataAnalytics da = new DataAnalytics();
    da.listAllFiles(f);

    System.out.println("Number of docs:" + da.reviews.size());
    System.out.println("Number of total sentences: " + da.getNumSentences());
    System.out.println("Number of min sentenes: " + da.getMinSentences());
    System.out.println("Number of max sentenes: " + da.getMaxSentences());
    System.out.println("Avg documeny length: " + da.getAvgDocLength());
    System.out.println("-------------------------------");
    System.out.println("Total number of tokens: " + da.getTotalTokens());
    System.out.println("Min number of tokens: " + da.getMinTokens());
    System.out.println("Max number of tokens: " + da.getMaxTokens());
    System.out.println("Avg number of tokens: " + da.getAvgTokens());
    System.out.println("-------------------------------");
    System.out.println("The avg sentence length: " + da.getAvgTokensPerSentence());
    System.out.println("Average sentence per document: ");
    da.outputAvgSentLenPerDoc();

  }
}

class Review {
  ArrayList<Sentence> sents;
  int numSents;
  String docName;

  Review(ArrayList<String> review, int length, String name) {
    this.sents = new ArrayList<Sentence>();
    this.numSents = length;
    int i = 0;

    for(String s: review) {
       this.sents.add(new Sentence(s));
    }
    this.docName = name;
  }

  int getSentLength() {
    return this.sents.size();
  }

  int getTotalTokens() {
    int total = 0;
    for(Sentence s: this.sents) {
      total += s.getNumTokens();
    }
    return total;
  }
}

class Sentence {
  String sentence;
  String[] tokens;

  Sentence(String s) {
      this.sentence = s;
      String[] toks = s.split(" ");
      this.tokens = toks;
  }

  int getNumTokens() {
    return this.tokens.length;
  }

}
