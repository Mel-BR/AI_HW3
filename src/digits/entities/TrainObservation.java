package digits.entities;

/* Class TrainObservation to contain the training 
 * observations it inherits from the class Observation */
public class TrainObservation extends Observation{
	
	/* Constructor 1 */
	public TrainObservation(int[][] features, int realLabel){
		super(features,realLabel);
	}	
	
	/* Constructor 2 */
	public TrainObservation(int[][] features){
		super(features);
	}
	
}
