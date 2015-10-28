package digits.entities;

import java.util.ArrayList;

/* This class will help to evaluate our model by
 * using a certain amount of metrics */
public class Evaluator {
	
	private ArrayList<TestObservation> testList;

	/* Constructor */
	public Evaluator(ArrayList<TestObservation> testList){
		this.testList = testList;	
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

}
