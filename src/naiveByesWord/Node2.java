package naiveByesWord;

public class Node2 {
	public String word;
	
	//Multinomial
	public double prob[] = {0,0};
	public int occur[] = {0,0};
	
	//Bernoulli
	public int occurPerInstance[] = {0,0};
	public double prob2[] = {0,0};
	
	
	
	public Node2(String word ){
		this.word = word;
	}


	public void calcProbs(int[] nrOfUniqueOccr, int[] totalNumbers) {
		//calculates the Likelihood with Laplacian smoothing
		//Multinomial Naive Bayes:
		this.prob[0] =  Math.log10(((float)this.occur[0]+1)/(totalNumbers[0]+nrOfUniqueOccr[0]));
		this.prob[1] =  Math.log10(((float)this.occur[1]+1)/(totalNumbers[1]+nrOfUniqueOccr[1]));

	} 
	
	public void calcProbs2(int[] typeOcc) {
		//calculates the Likelihood with Laplacian smoothing
		//Bernoulli Naive Bayes:
		this.prob2[0] =  Math.log10(((float)this.occurPerInstance[0]+1)/(typeOcc[0]));
		this.prob2[1] =  Math.log10(((float)this.occurPerInstance[1]+1)/(typeOcc[1]));

	} 
	
	
}
