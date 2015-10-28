package digits.entities;

abstract public class Observation {
	
	private int[][] features;
	private int realLabel;
	
	public Observation(int[][] features, int realLabel){
		this.features = features;
		this.realLabel = realLabel;
	}	
	
	public Observation(int[][] features){
		this.features = features;
	}
	
	public int getFeatures(int i, int j){
		return features[i][j];
	}
	
	public int getRealLabel(){
		return realLabel;
	}
	
	public void setRealLabel(int realLabel){
		this.realLabel = realLabel;
	}
}
