package digits.entities;


/* Abstract class Observation */
abstract public class Observation {
	
	private int[][] features;
	private int realLabel;
	
	/* Constructor 1 */
	public Observation(int[][] features, int realLabel){
		this.features = features;
		this.realLabel = realLabel;
	}	
	
	/* Constructor 2 */
	public Observation(int[][] features){
		this.features = features;
	}
	
	public int getFeature(int i, int j){
		return features[i][j];
	}
	
	public int getRealLabel(){
		return realLabel;
	}
	
	public void setRealLabel(int realLabel){
		this.realLabel = realLabel;
	}
}
