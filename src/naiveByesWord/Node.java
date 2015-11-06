package naiveByesWord;

public class Node {
	public String word;
	
	public double prob = 0;
	
	public int occur = 0;
	
	
	
	
	public Node(String word ){
		this.word = word;
	}


	public void calcProbs(int nrOfUniqueOccr, int totalNumbers) {
		//calculates the posteriur with Laplacian smoothing
		this.prob =  Math.log10(((float)this.occur+1)/(totalNumbers+nrOfUniqueOccr));
	} 
	
	
}
