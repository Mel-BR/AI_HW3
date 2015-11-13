package naiveByesWord;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Classifier {
	Map<String, Node> dictionary;
	LinkedList<Node> wordList;

	
	float[] prior;
	int[] types;
	double correctness[] = new double[2]; 
	double confusionMatrix[][][] = new double[2][2][2];
	
	public Classifier(String traingFileName, String testFilename, int type1, int type2){
		
		dictionary = new HashMap<String, Node>();
		wordList = new LinkedList<Node>();
		prior = new float[2];
		types = new int[2];
		types[0] = type1;
		types[1] = type2;
		Parser.readData(types, traingFileName,dictionary,wordList,prior);
		
		
		testData(testFilename);
		
		System.out.println("\n----\nMultinomial Naive Bayes classification rate: "+round(correctness[0],3));
		System.out.println("Bernoulli Naive Bayes classification rate: "+round(correctness[1],3));
		
		System.out.println("\nConfusion Matrix, Multinomial Naive Bayes:");
		System.out.println("|"+round(confusionMatrix[0][0][0],3)+"\t" +round(confusionMatrix[0][0][1],3)+"|");
		System.out.println("|"+round(confusionMatrix[0][1][0],3)+"\t" +round(confusionMatrix[0][1][1],3)+"|");
		System.out.println(" ");
		
		System.out.println("\nConfusion Matrix, Bernoulli Naive Bayes:");
		System.out.println("|"+round(confusionMatrix[1][0][0],3)+"\t" +round(confusionMatrix[1][0][1],3)+"|");
		System.out.println("|"+round(confusionMatrix[1][1][0],3)+"\t" +round(confusionMatrix[1][1][1],3)+"|");
		System.out.println(" ");

		
		printTop20probs();
	}
	
	public void printTop20probs(){
		Node[][] topList=getTop20probs();
		
		System.out.println("\nMultinomial Naive Bayes,Log10, Class: "+types[0]);
		for(int i = 0; 20>i;i++){
			System.out.printf("..%-10s\t%.3f\n",topList[0][i].word,round(topList[0][i].prob[0],3));
//			System.out.println(".."+topList[0][i].word+"\t\t "+round(topList[0][i].prob[0],3));
		}
		
		System.out.println("\nMultinomial Naive Bayes,Log10, Class: "+types[1]);

		for(int i = 0; 20>i;i++){
			System.out.printf("..%-10s\t%.3f\n",topList[1][i].word,round(topList[1][i].prob[1],3));

//			System.out.println(".."+topList[1][i].word+"\t\t "+round(topList[1][i].prob[0],3));
		}
		
		System.out.println("\nBernoulli Naive Bayes,Log10, Class: "+types[0]);

		for(int i = 0; 20>i;i++){
			System.out.printf("..%-10s\t%.3f\n",topList[2][i].word,round(topList[2][i].prob2[0],3));

//			System.out.println(".."+topList[2][i].word+"\t\t "+round(topList[2][i].prob[1],3));
		}
		
		System.out.println("\nBernoulli Naive Bayes,Log10, Class: "+types[1]);

		for(int i = 0; 20>i;i++){			
			System.out.printf("..%-10s\t%.3f\n",topList[3][i].word,round(topList[3][i].prob2[1],3));
//			System.out.println(".."+topList[3][i].word+"\t\t "+round(topList[3][i].prob[1],3));
		}
		
	}
	
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public Node[][] getTop20probs(){
		Node[][] topList = new Node[4][20];
		
		//Multinomial Naive Bayes
		int nrOfWords = wordList.size();
		ArrayList<Node> tempAllWordsType1 = new ArrayList<Node>();
		ArrayList<Node> tempAllWordsType2 = new ArrayList<Node>();

		for(int i = 0; nrOfWords>i;i++){
			tempAllWordsType1.add(wordList.get(i));
			tempAllWordsType2.add(wordList.get(i));
		}
		
		//Reversed sort
		Collections.sort(tempAllWordsType1, new Comparator<Node>() {
	        @Override public int compare(Node p1, Node p2) {
	        	if (0>(p1.prob[0] - p2.prob[0])){
	        		return 1;
	        	}else if (0<(p1.prob[0] - p2.prob[0])){
	        		return -1;
	        	}else{
	            return 0; // Ascending
	            }
	        }
		});
		Collections.sort(tempAllWordsType2, new Comparator<Node>() {
	        @Override public int compare(Node p1, Node p2) {
	        	if (0>(p1.prob[1] - p2.prob[1])){
	        		return 1;
	        	}else if (0<(p1.prob[1] - p2.prob[1])){
	        		return -1;
	        	}else{
	            return 0; // Ascending
	            }
	        }
		});
		
		for(int i = 0; 20>i;i++){
			topList[0][i] = tempAllWordsType1.get(i);
			topList[1][i] = tempAllWordsType2.get(i);

		}
		
		
//		Arrays.sort( tempAllWordsType1 );
//		Arrays.sort( tempAllWordsType2 );
		
//		reverseInPlace(tempAllWordsType1);
//		reverseInPlace(tempAllWordsType2);

//		topList[0] = Arrays.copyOfRange(tempAllWordsType1, 0, 20);
//		topList[1] = Arrays.copyOfRange(tempAllWordsType2, 0, 20);
		


		//Bernoulli Naive Bayes
		ArrayList<Node> tempAllWordsType3 = new ArrayList<Node>();
		ArrayList<Node> tempAllWordsType4 = new ArrayList<Node>();

		for(int i = 0; nrOfWords>i;i++){
			tempAllWordsType3.add(wordList.get(i));
			tempAllWordsType4.add(wordList.get(i));
		}
		
		//Reversed sort
		Collections.sort(tempAllWordsType3, new Comparator<Node>() {
	        @Override public int compare(Node p1, Node p2) {
	        	if (0>(p1.prob2[0] - p2.prob2[0])){
	        		return 1;
	        	}else if (0<(p1.prob2[0] - p2.prob2[0])){
	        		return -1;
	        	}else{
	            return 0; // Ascending
	            }
	        }
		});
		
		Collections.sort(tempAllWordsType4, new Comparator<Node>() {
	        @Override public int compare(Node p1, Node p2) {
	        	if (0>(p1.prob2[1] - p2.prob2[1])){
	        		return 1;
	        	}else if (0<(p1.prob2[1] - p2.prob2[1])){
	        		return -1;
	        	}else{
	            return 0; // Ascending
	            }
	        }
		});
		
		for(int i = 0; 20>i;i++){
			topList[2][i] = tempAllWordsType3.get(i);
			topList[3][i] = tempAllWordsType4.get(i);

		}
		
		
		
//		
//		double[] tempAllWordsType3 = new double[nrOfWords];
//		double[] tempAllWordsType4 = new double[nrOfWords];
//
//		for(int i = 0; nrOfWords>i;i++){
//			tempAllWordsType3[i] = wordList.get(i).prob2[0];
//			tempAllWordsType4[i] = wordList.get(i).prob2[1];
//		}
//		Arrays.sort( tempAllWordsType3 );
//		Arrays.sort( tempAllWordsType4 );
//		
//		reverseInPlace(tempAllWordsType3);
//		reverseInPlace(tempAllWordsType4);
//		
//		topList[2] = Arrays.copyOfRange(tempAllWordsType3, 0, 20);
//		topList[3] = Arrays.copyOfRange(tempAllWordsType4, 0, 20);
//		
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
	
	
	
	public void testData(String filename){
		BufferedReader bf = Parser.createBufferedReader(filename);
		int rightAnswers1Type1 = 0;
		int wrongAnswers1Type1 = 0;
		
		int rightAnswers1Type2 = 0;
		int wrongAnswers1Type2 = 0;
		
		int rightAnswers2Type1 = 0;
		int wrongAnswers2Type1 = 0;
		
		int rightAnswers2Type2 = 0;
		int wrongAnswers2Type2 = 0;
		
		try {
			String aLine;
			int iii = 0;
			while((aLine = bf.readLine()) != null){
				iii ++;
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
					
					if(typeCorrect == this.types[0]){
						if(typeAnswer1==typeCorrect){
							rightAnswers1Type1++;
						}else{
							wrongAnswers1Type1++;
//							System.out.println("wrongAnswers1Type1++line="+iii);

						}
						if(typeAnswer2==typeCorrect){
							rightAnswers2Type1++;
						}else{
							wrongAnswers2Type1++;
						}
					}else if(typeCorrect == this.types[1]){
						
						if(typeAnswer1==typeCorrect){
							rightAnswers1Type2++;
						}else{
							wrongAnswers1Type2++;
						}
						if(typeAnswer2==typeCorrect){
							rightAnswers2Type2++;
						}else{
							wrongAnswers2Type2++;
						}
						
						
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
		
		
		correctness[0] = (double) (rightAnswers1Type1+rightAnswers1Type2)/(rightAnswers1Type1+rightAnswers1Type2+wrongAnswers1Type1+wrongAnswers1Type2);
		correctness[1] = (double) (rightAnswers2Type1+rightAnswers2Type2)/(rightAnswers2Type1+rightAnswers2Type2+wrongAnswers2Type1+wrongAnswers2Type2);
		//=====
		confusionMatrix[0][0][0] = (double) (rightAnswers1Type1)/(rightAnswers1Type1+wrongAnswers1Type1);
		confusionMatrix[0][0][1] = (double) 1-confusionMatrix[0][0][0];
		
		confusionMatrix[0][1][1] = (double) (rightAnswers1Type2)/(rightAnswers1Type2+wrongAnswers1Type2);
		confusionMatrix[0][1][0] = (double) 1-confusionMatrix[0][1][1];
		//=====
		confusionMatrix[1][0][0] = (double) (rightAnswers2Type1)/(rightAnswers2Type1+wrongAnswers2Type1);
		confusionMatrix[1][0][1] = (double) 1-confusionMatrix[1][0][0];
		
		confusionMatrix[1][1][1] = (double) (rightAnswers2Type2)/(rightAnswers2Type2+wrongAnswers2Type2);
		confusionMatrix[1][1][0] = (double) 1-confusionMatrix[1][1][1];
		
		//System.out.println("====rightAnswers2Type1====="+rightAnswers2Type1);
//		System.out.println("===wrongAnswers2Type1====="+wrongAnswers2Type1);

	}

}
