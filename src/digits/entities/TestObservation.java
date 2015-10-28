package digits.entities;

/* Class TrainObservation to contain the training 
 * observations it inherits from the class Observation */
public class TestObservation extends Observation{
	
	private int predictedLabel;
	
	/* Constructor 1 */
	public TestObservation(int[][] features, int realLabel){
		super(features,realLabel);
	}	
	
	/* Constructor 2 */
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