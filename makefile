all: DataAnalytics FeatureSelection CorpusFilter SplitDataset BayesClassifier StopWordRemoval  GradientDecent SimpleLog

DataAnalytics:
	javac ./DataAnalytics.java

FeatureSelection:
	javac -cp ./opennlp-tools-1.9.1.jar:. ./FeatureSelection.java

CorpusFilter:
	javac -cp ./weka.jar:. CorpusFilter.java 

SplitDataset:
	javac -cp ./weka.jar:. SplitDataset.java

BayesClassifier:
	javac -cp ./weka.jar:. BayesClassifier.java

GradientDecent:
	javac -cp ./weka.jar:. GradientDecent.java

StopWordRemoval:
	javac StopWordRemoval.java

SimpleLog:
	javac -cp ./weka.jar:. SimpleLog.java

run-feature:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1000

run-dataAnalytics:
	java DataAnalytics > Corpus_anlysis.txt

run-corpusfilter:
	java -cp weka.jar:. CorpusFilter features.txt

run-splitdataset:
	java -cp ./weka.jar:. SplitDataset

run-bayesclass:
	java -cp ./weka.jar:. BayesClassifier

run-gradient-decent:
	java -cp ./weka.jar:. GradientDecent

run-stopWordRemoval:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection

run-new-corpusloader:
	java -cp ./weka.jar:. NewCorpusLoader

run-all-bayes: 
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 0 0
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 3000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier




run-all-bayes-stopword:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 0 0
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-bayes-stopword-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 3000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. BayesClassifier

run-all-sgd:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 0 0
	java CorpusFilter features.txt
	java -cp ./weka.jar:. NewCorpusLoader
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent.java

run-all-sgd-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 3000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 0 0
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent

run-all-sgd-stopword-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 3000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. GradientDecent


run-all-simplelog-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 1500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 2500
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 1 3000
	java -cp ./weka.jar:. CorpusFilter features.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-1000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-1500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 1500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-2000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-2500:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 2500
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

run-all-simplelog-stopword-3000:
	java -cp ./opennlp-tools-1.9.1.jar:. FeatureSelection 20 3000
	java StopWordRemoval
	java -cp ./weka.jar:. CorpusFilter ./stop_word_feat.txt
	java -cp ./weka.jar:. SplitDataset
	java -cp ./weka.jar:. SimpleLog

clean:
	rm *.class