package digits;

import java.text.DecimalFormat;
import java.util.ArrayList;

import digits.entities.Classifier;
import digits.entities.Evaluator;
import digits.entities.HelpFuncs;
import digits.entities.TestObservation;
import digits.entities.TrainObservation;

public class DigitsClassification {

	public static void main(String arg[]){
		
		System.out.println("Reading training observations...");
		ArrayList<TrainObservation> trainObsList = HelpFuncs.buildTrainObs("trainingimages","traininglabels");		
		
		System.out.println("Reading test observations...");
		ArrayList<TestObservation> testObsList = HelpFuncs.buildTestObs("testimages","testlabels");

		System.out.println("Done");
		
		Classifier classifier = new Classifier(28, 10, 2, trainObsList.size(), 1);
		classifier.train(trainObsList);
		ArrayList<TestObservation> testObsListLabeled = classifier.test(testObsList);

		/*for(TestObservation testObs : testObsListLabeled){
			System.out.println(testObs.getRealLabel()+" "+testObs.getPredictedLabel());
		}*/
		
		
		Evaluator eval = new Evaluator(testObsListLabeled,classifier);
		
		// Displaying accuracy
		for(int i=0;i<10;i++){
			System.out.println("Accuracy for label "+i+" : "+eval.getAccuracy(i));
		}
		
		System.out.println();
		
		//Displaying Confusion Matrix
		float[][] confMat = eval.generateConfMatrix(testObsListLabeled);
		DecimalFormat formatter = new DecimalFormat("00.00");
		for(int i=0;i<10;i++){		
			for(int j=0;j<10;j++){
				System.out.printf(formatter.format(confMat[i][j]*100));
				System.out.print("  ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		
		//Displaying Highest Confusion Rate Values
		System.out.println("Highset Confusion Rate values:");
		int[][] highestVal = eval.getHighestVal2(4);
		for(int i=0;i<highestVal.length;i++){		
			for(int j=0;j<highestVal[i].length;j++){
				System.out.print(highestVal[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		// Displaying Likelihood maps
		for(int i=0;i<10;i++){
			System.out.println("Likelihood Map for label "+i+" : ");
			eval.displayLikelihoodMap(i);
		}		
		
		System.out.println();
		
		// displaying odd ratios maps : Test
		eval.displayOddRatiosMap(8, 1);
		
		
		
		
		
	}	
}
