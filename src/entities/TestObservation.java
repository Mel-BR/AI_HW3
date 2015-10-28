package entities;

public class TestObservation extends Observation{
	
	private int predictedLabel;
	
	public TestObservation(int[][] features, int realLabel){
		super(features,realLabel);
	}	
	
	public TestObservation(int[][] features){
		super(features);
	}
	
	public void setPredictedLabel(int predictedLabel){
		this.predictedLabel = predictedLabel;
	}
	
	public int getPredictedLabel(){
		return predictedLabel;
	}
	
}