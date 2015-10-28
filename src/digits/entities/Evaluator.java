package digits.entities;

import java.util.ArrayList;

public class Evaluator {
	
	private ArrayList<TestObservation> testList;

	public Evaluator(ArrayList<TestObservation> testList){
		this.testList = testList;	
	}
	
	public void setTestList(ArrayList<TestObservation> testList){
		this.testList = testList;	
	}
	
	public float getAccuracy(int label){
		int countRealLabel = 0;
		int countCorrectedPredictedLabel = 0;
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
