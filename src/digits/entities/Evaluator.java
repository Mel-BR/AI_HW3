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
	
	public int[][] getHighestVal(float [][] in, int number){
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
	}
}
