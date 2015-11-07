package naiveByesWord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Classifier {
	Map<String, Node> dictionary;
	LinkedList<Node> wordList;

	
	float[] prior;
	int[] types;
	
	public Classifier(String traingFileName, String testFilename, int type1, int type2){
		
		dictionary = new HashMap<String, Node>();
		wordList = new LinkedList<Node>();
		prior = new float[2];
		types = new int[2];
		types[0] = type1;
		types[1] = type2;
		Parser.readData(types, traingFileName,dictionary,wordList,prior);
		
		double correctness[] = new double[2];
		correctness = testData(testFilename);
		
		System.out.println("Multinomial Naive Bayes correctness:"+correctness[0]);
		System.out.println("Bernoulli Naive Bayes correctness:"+correctness[1]);
		
		printTop20probs();
	}
	
	public void printTop20probs(){
		double[][] topList=getTop20probs();
		
		for(int ii = 0; 4>ii;ii++){
			System.out.println("\n       colum: "+ii);
			for(int i = 0; 20>i;i++){
				System.out.println(".."+topList[ii][i]+"..");
			}
		}
	}
	
	
	
	public double[][] getTop20probs(){
		double[][] topList = new double[4][20];
		
		//Multinomial Naive Bayes
		int nrOfWords = wordList.size();
		double[] tempAllWordsType1 = new double[nrOfWords];
		double[] tempAllWordsType2 = new double[nrOfWords];

		for(int i = 0; nrOfWords>i;i++){
			tempAllWordsType1[i] = wordList.get(i).prob[0];
			tempAllWordsType2[i] = wordList.get(i).prob[1];
		}
		Arrays.sort( tempAllWordsType1 );
		Arrays.sort( tempAllWordsType2 );
		
		reverseInPlace(tempAllWordsType1);
		reverseInPlace(tempAllWordsType2);

		topList[0] = Arrays.copyOfRange(tempAllWordsType1, 0, 20);
		topList[1] = Arrays.copyOfRange(tempAllWordsType2, 0, 20);
		

		//Bernoulli Naive Bayes
		double[] tempAllWordsType3 = new double[nrOfWords];
		double[] tempAllWordsType4 = new double[nrOfWords];

		for(int i = 0; nrOfWords>i;i++){
			tempAllWordsType3[i] = wordList.get(i).prob2[0];
			tempAllWordsType4[i] = wordList.get(i).prob2[1];
		}
		Arrays.sort( tempAllWordsType3 );
		Arrays.sort( tempAllWordsType4 );
		
		reverseInPlace(tempAllWordsType3);
		reverseInPlace(tempAllWordsType4);
		
		topList[2] = Arrays.copyOfRange(tempAllWordsType3, 0, 20);
		topList[3] = Arrays.copyOfRange(tempAllWordsType4, 0, 20);
		
		return topList;
	}
	
	
	public static void reverseInPlace(double[] x) {
	    double tmp;    

	    for (int i = 0; i < x.length / 2; i++) {
	        tmp = x[i];
	        x[i] = x[(x.length - 1 - i)];
	        x[x.length - 1 - i] = tmp;
	    }
	}
	
	public double[] testData(String filename){
		BufferedReader bf = Parser.createBufferedReader(filename);
		int rightAnswers1 = 0;
		int wrongAnswers1 = 0;
		
		int rightAnswers2 = 0;
		int wrongAnswers2 = 0;
		
		try {
			String aLine;
			while((aLine = bf.readLine()) != null){
				LinkedList<Node> wordListTest = new LinkedList<Node>();
				int typeCorrect = Parser.fillWordListFromLine(aLine,wordListTest);
				int typeAnswer1 = -9;
				int typeAnswer2 = -9;
				if(typeCorrect==this.types[0] || typeCorrect==this.types[1]){
					double posterior1 = 0;
					double posterior2 = 0;
					
					double posterior12 = 0;
					double posterior22 = 0;
					for (int i=0;wordListTest.size()>i;i++){
						if(dictionary.containsKey(wordListTest.get(i).word)){
						posterior1+=prior[0]*dictionary.get(wordListTest.get(i).word).prob[0];
						posterior2+=prior[1]*dictionary.get(wordListTest.get(i).word).prob[1];
						
						posterior12+=prior[0]*dictionary.get(wordListTest.get(i).word).prob2[0];
						posterior22+=prior[1]*dictionary.get(wordListTest.get(i).word).prob2[1];
						}
					}
					
					if(posterior1>posterior2){
						typeAnswer1 = this.types[0];
					}else{
						typeAnswer1 = this.types[1];
					}
					if(posterior12>posterior22){
						typeAnswer2 = this.types[0];
					}else{
						typeAnswer2 = this.types[1];
					}
					
					if(typeAnswer1==typeCorrect){
						rightAnswers1++;
					}else{
						wrongAnswers1++;
					}
					if(typeAnswer2==typeCorrect){
						rightAnswers2++;
					}else{
						wrongAnswers2++;
					}
					
				}else{
					System.out.println("Not expected Type, test reading");
				}
			}
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double correctness[] = new double[2];
		correctness[0] = (double) rightAnswers1/(rightAnswers1+wrongAnswers1);
		correctness[1] = (double) rightAnswers2/(rightAnswers2+wrongAnswers2);

		return correctness;
	}

}
