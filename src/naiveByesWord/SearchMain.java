package naiveByesWord;

public class SearchMain {
	
	
	
	
	
	public static void main(String arg[]){
		Classifier emailFilter = new Classifier("train_email.txt","test_email.txt",1,0);
		
		Classifier movieRater = new Classifier("rt-train.txt","rt-test.txt",1,-1);

		
	}
	
}
