package digits.entities;

import java.util.ArrayList;

public class Classifier {
	
	private int[] countClass; // number of example for a given class in the training set. 
	private int[][][][] countPixValueClass; // number of times pixel (i,j) has value f in training examples for a given class	
	private int totalNumberOfObservations;
	private int imageSize;
	private int nbOfClass;
	
	public Classifier(int imageSize, int nbOfClass, int numberOfValues, int totalNumberOfObservations){
		this.countClass = new int[nbOfClass];
		this.countPixValueClass = new int[imageSize][imageSize][numberOfValues][nbOfClass];
		this.totalNumberOfObservations = totalNumberOfObservations;
		this.imageSize = imageSize;
	}
	
	public void train(ArrayList<TrainObservation> trainList){
		
		for(TrainObservation trainObs : trainList){
			int label = trainObs.getRealLabel();
			//increment the count for the given class
			countClass[label]++;
			//increment the total number of observation
			totalNumberOfObservations++;
			for(int i = 0 ; i < imageSize ; i++){
				for(int j = 0 ; j < imageSize ; j++){
					int value = trainObs.getFeature(i, j);
					countPixValueClass[i][j][value][label]++;
				}
			}
		}	
	}
	
	private float getPriorProb(int label){
		return (float)countClass[label]/totalNumberOfObservations;
	}
	
	private float getLikelihood(int i,int j, int value,int label){
		return (float)countPixValueClass[i][j][value][label]/countClass[label];
	}
	
	private double getPosteriorProb(int label, Observation obs){
		double result = Math.log(getPriorProb(label));
		for(int i = 0 ; i < imageSize ; i++){
			for(int j = 0 ; j < imageSize ; j++){
				int value = obs.getFeature(i, j);
				result += Math.log(getLikelihood(i, j, value, label));
			}
		}
		return result;
	}

	
	public int getBestClass(TestObservation testObs){
		int result = 0;
		double bestPosteriorProb = Double.NEGATIVE_INFINITY;
		for (int i = 0 ; i < nbOfClass ; i++){
			double posterioProb = getPosteriorProb(i, testObs);
			if( posterioProb < bestPosteriorProb){
				result = i;
				bestPosteriorProb = posterioProb;
			}
		}
		return result;
	}
	
	public ArrayList<TestObservation> test(ArrayList<TestObservation> testList){
		for (TestObservation testObs : testList){
			testObs.setPredictedLabel(getBestClass(testObs));
		}
		return testList;
	}
}
