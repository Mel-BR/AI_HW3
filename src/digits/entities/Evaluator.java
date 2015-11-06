package digits.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/* This class will help to evaluate our model by
 * using a certain amount of metrics */
public class Evaluator {

	private ArrayList<TestObservation> testList;
	private Classifier classifier;

	/* Constructor */
	public Evaluator(ArrayList<TestObservation> testList, Classifier classifier){
		this.testList = testList;	
		this.classifier = classifier;
	}

	/* Sets a new test observations list */
	public void setTestList(ArrayList<TestObservation> testList){
		this.testList = testList;	
	}

	/* Returns the accuracy for a given class */
	public float getAccuracy(int label){
		int countRealLabel = 0; // The number of test observations that have the given label as RealLabel
		int countCorrectedPredictedLabel = 0; // The number of test observations that have the given label as RealLabel AND as PredictedLabel

		// We browse the list of test observation */
		for(TestObservation testObs : testList){
			if(testObs.getRealLabel() == label){
				countRealLabel++;
				if(testObs.getRealLabel() == testObs.getPredictedLabel() ){
					countCorrectedPredictedLabel++;
				}
			}
		}
		return (float)countCorrectedPredictedLabel/countRealLabel;
	}

	public float getGeneralAccuracy(){
		int countAll= 0; // The number of test observations
		int countCorrectedPredictedLabel = 0; // The number of test observations that have the same RealLabel and PredictedLabel

		// We browse the list of test observation */
		for(TestObservation testObs : testList){
			countAll++;
			if(testObs.getRealLabel() == testObs.getPredictedLabel() ){
				countCorrectedPredictedLabel++;
			}
		}
		return (float)countCorrectedPredictedLabel/countAll;
	}

	public float[][] generateConfMatrix(ArrayList<TestObservation> obs){
		float[][] ret = new float [10][10];
		int[] counter = new int[10];
		for (TestObservation it : obs){
			counter[it.getRealLabel()]++;
			ret[it.getPredictedLabel()][it.getRealLabel()]++;
		}
		for (int i=0; i<ret.length; i++){
			for (int j =0; j< ret[0].length; j++){
				ret[i][j]/=counter[i];
			}
		}
		return ret;
	}

	public float getOddRatio(int i, int j, int ci, int cii){
		if (i<0 || i>27 || j<0 || j>27 || ci>9 || cii>9){
			System.out.println("getOddRatio wrong input");
			return -1;
		}
		return (classifier.getLikelihood(i,j,1,ci)/classifier.getLikelihood(i,j,1,cii));
	}

	public float[][] getOddRatios(int ci, int cii){
		float [][] ret = new float[classifier.getSize()][classifier.getSize()];
		for (int i = 0; i<classifier.getSize(); i++){
			for (int j = 0; j<classifier.getSize(); j++){
				ret[i][j] = getOddRatio(i,j,ci,cii);
			}
		}
		return ret;
	}

	/*public int[][] getHighestVal(float [][] in, int number){
		int [][] ret = new int[number][2];
		Comparator<int[]> comparator = new myComparator(in);
		PriorityQueue<int[]> queue = new PriorityQueue<int[]>(in.length*in[0].length, comparator);
		for (int i = 0; i<in.length; i++){
			for (int j = 0; j<in[0].length; j++){
				queue.add(new int[]{i,j});
			}
		}
		for (int i =0; i<number; i++){
			ret[i]=queue.remove();
		}
		return ret;
	}

	public class myComparator implements Comparator<int[]>{
		float [][]in;
		public myComparator(float[][]in){
			this.in = in;
		}
		public int compare(int[] a, int[] b){
			return (int) ((in[a[0]][a[1]] - in[b[0]][b[1]])*1000);
		}
	}*/


	/* Returns the pairs with the highest value for the confusion rate
	 * rows represent the rank and 1st column represent the first value 
	 * and second column the second one*/
	public int[][] getHighestVal2(int number){
		int[][] ret = new int[4][2];
		float[][] confMat = generateConfMatrix(testList);
		HeapElementComparator comparator = new HeapElementComparator();
		PriorityQueue<HeapElement> priorityQueue = new PriorityQueue<HeapElement>(number,comparator);
		// Browse the confusion matrix and add each confusion rate with the related pairs to the priority queue
		for (int i = 0; i<10; i++){
			for (int j = 0; j<10; j++){
				if(i!=j){
					priorityQueue.add(new HeapElement(confMat[i][j], i, j));
				}
			}
		}
		/* We fill the 2-dim result array with the pairs of highest value
		 * from the priority queue */
		HeapElement currElem;
		for (int i =0; i<number; i++){
			currElem = priorityQueue.remove();
			ret[i][0]=currElem.getLabel1();
			ret[i][1]=currElem.getLabel2();;
		}
		return ret;
	}

	public void displayLikelihoodMap(int label){
		for (int i = 0; i<28; i++){
			for (int j = 0; j<28; j++){
				if(classifier.getLikelihood(i, j, 1, label)<0.1){
					System.out.print(" ");
				}
				else if(classifier.getLikelihood(i, j, 1, label)<0.5){
					System.out.print("-");
				}
				else{
					System.out.print("+");
				}

			}
			System.out.println();
		}

	}	

	public void displayOddRatiosMap(int label1, int label2){
		for (int i = 0; i<28; i++){
			for (int j = 0; j<28; j++){
				double logOddRation = Math.log(getOddRatio(i, j, label1, label2));
				if(logOddRation < 0 ){
					System.out.print(" ");
				}
				else if(logOddRation < 1.3){
					System.out.print("-");
				}
				else{
					System.out.print("+");
				}

			}
			System.out.println();
		}

	}

	public void displayLowest(){
		double min = Double.POSITIVE_INFINITY;
		TestObservation ret = null;
		for (int i = 0; i < 10; i++){
			for (TestObservation it : testList){
				double here = classifier.getPosteriorProb(i, it);
				if (here < min){
					min = here;
					ret = it;
				}
			}
		}
		(ret).displayFeatures();
		}
	
	public void displayHighest(){
		double min = Double.NEGATIVE_INFINITY;
		TestObservation ret = null;
		for (int i = 0; i < 10; i++){
			for (TestObservation it : testList){
				double here = classifier.getPosteriorProb(i, it);
				if (here > min){
					min = here;
					ret = it;
				}
			}
		}
		(ret).displayFeatures();
		}
}
