package entities;

public class TrainObservation extends Observation{
	
	public TrainObservation(int[][] features, int realLabel){
		super(features,realLabel);
	}	
	
	public TrainObservation(int[][] features){
		super(features);
	}
	
}
