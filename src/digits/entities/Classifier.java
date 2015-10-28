package digits.entities;

import java.util.ArrayList;

public class Classifier {
	
	private int[] countClass; // number of example for a given class in the training set. 
	private int[][][][] countPixValueClass; // number of times pixel (i,j) has value f in training examples for a given class	
	private int totalNumberOfObservations;
	private ArrayList<TrainObservation> trainList;
	private ArrayList<TestObservation> testList;
	private int imageSize;
	private int nbOfClass;
	
	public Classifier(int imageSize, int nbOfClass, int numberOfValues, int totalNumberOfObservations, ArrayList<TrainObservation> trainList, ArrayList<TestObservation> testList ){
		this.countClass = new int[nbOfClass];
		this.countPixValueClass = new int[imageSize][imageSize][numberOfValues][nbOfClass];
		this.totalNumberOfObservations = totalNumberOfObservations;
		this.trainList = trainList;
		this.testList = testList;
		this.imageSize = imageSize;
		
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
	
	public float getPriorProb(int label){
		return (float)countClass[label]/totalNumberOfObservations;
	}
	
	public float getLikelihood(int i,int j, int value,int label){
		return (float)countPixValueClass[i][j][value][label]/countClass[label];
	}
	
	public double getPosteriorProb(int label, Observation obs){
		double result = Math.log(getPriorProb(label));
		for(int i = 0 ; i < imageSize ; i++){
			for(int j = 0 ; j < imageSize ; j++){
				result += Math.log(getPosteriorProb(label, obs));
			}
		}
		return result;
	}

	
	public int getBestClass(TrainObservation trainObs){
		int result = 0;
		double bestPosteriorProb = Double.NEGATIVE_INFINITY;
		for (int i = 0 ; i < nbOfClass ; i++){
			double posterioProb = getPosteriorProb(i, trainObs);
			if( posterioProb < bestPosteriorProb){
				result = i;
				bestPosteriorProb = posterioProb;
			}
		}
		return result;
	}
}
