package digits.entities;


/*
 * This class allows us to keep track of
 * the pairs of class that have the highest
 * confusion rates
 */
public class HeapElement {
	
	private float confRate;
	private int label1;
	private int label2;
	
	public HeapElement(float confRate, int label1, int label2){
		this.confRate = confRate;
		this.label1 = label1;
		this.label2 = label2;
	}
	
	public float getConfRate(){
		return confRate;
	}	
	
	public int getLabel1(){
		return label1;
	}
	
	public int getLabel2(){
		return label2;
	}
	
}
