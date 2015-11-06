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
	
	// Browse the feature attribute and display values
	public void displayFeatures(){
		System.out.println();
		for(int i=0;i<features.length;i++){
			for(int j=0;j<features[i].length;j++){
				System.out.print(features[i][j]);
			}
			System.out.println();
		}
	}
}
