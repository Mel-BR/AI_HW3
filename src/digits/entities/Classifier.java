package digits.entities;

import java.util.ArrayList;

public class Classifier {
	
	private int[] countClass; // number of example for a given class in the training set. 
	private int[][][][] countPixValueClass; // number of times pixel (i,j) has value f in training examples for a given class	
	private int totalNumberOfObservations; // total number of observations
	private int imageSize; // size of the image, in our example : 28
	private int nbOfClass; // total number of class in the problem
	private int k; // smoothing parameter
	private int numberOfValues;
	
	/* Constructor */
	public Classifier(int imageSize, int nbOfClass, int numberOfValues, int totalNumberOfObservations, int k){
		this.nbOfClass = nbOfClass;
		this.countClass = new int[nbOfClass];
		this.countPixValueClass = new int[imageSize][imageSize][numberOfValues][nbOfClass];
		this.totalNumberOfObservations = totalNumberOfObservations;
		this.imageSize = imageSize;
		this.numberOfValues = numberOfValues;
		this.k = k;
	}
	
	/* Train the classifier, takes as input the training observations and compute the different parameters of our model */
	public void train(ArrayList<TrainObservation> trainList){
		this.countClass = new int[nbOfClass];
		this.countPixValueClass = new int[imageSize][imageSize][numberOfValues][nbOfClass];
		
		for(TrainObservation trainObs : trainList){
			int label = trainObs.getRealLabel();
			//increment the count for the given class
			countClass[label]++;
			//increment the total number of observation
			for(int i = 0 ; i < imageSize ; i++){
				for(int j = 0 ; j < imageSize ; j++){
					int value = trainObs.getFeature(i, j);
					countPixValueClass[i][j][value][label]++;
				}
			}
		}	
	}
	
	/* Gives the prior probability given a class */
	public float getPriorProb(int label){
		return (float)countClass[label]/totalNumberOfObservations;
	}
	
	/* Gives the likelihood for a given feature (i,j) , a value and a class */
	public float getLikelihood(int i,int j, int value,int label){		
		return ((float)countPixValueClass[i][j][value][label]+k)/(countClass[label]+k*numberOfValues);
	}
	
	/* Gives the posterior prob for a given observation and a given class */
	public double getPosteriorProb(int label, Observation obs){
		double result = Math.log(getPriorProb(label));
		for(int i = 0 ; i < imageSize ; i++){
			for(int j = 0 ; j < imageSize ; j++){
				int value = obs.getFeature(i, j);
				result += Math.log(getLikelihood(i, j, value, label));
			}
		}
		return result;
	}

	/* Gives the best class given an observation */
	public int getBestClass(TestObservation testObs){
		int result = 0;
		double bestPosteriorProb = Double.NEGATIVE_INFINITY;
		for (int i = 0 ; i < nbOfClass ; i++){
			double posterioProb = getPosteriorProb(i, testObs);
			if( posterioProb > bestPosteriorProb){
				result = i;
				bestPosteriorProb = posterioProb;
			}
		}
		return result;
	}
	
	/* Run the tests on a given list of test observations and assign them
	 *  the best predicted class accord to the model */
	public ArrayList<TestObservation> test(ArrayList<TestObservation> testList){
		for (TestObservation testObs : testList){
			testObs.setPredictedLabel(getBestClass(testObs));
		}
		return testList;
	}
	
	/* Getter for the image size */
	public int getSize(){
		return imageSize;
	}
	
	/* Setter for the parameter k (smoothing) */
	public void setK(int k){
		this.k=k;
	}
}
