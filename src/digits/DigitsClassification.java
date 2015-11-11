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

		
		// Trying to find the best value for the smoothing parameter k 
		// Create classifier, initialize smoothing parameter to 1
		Classifier classifierTest = new Classifier(28, 10, 2, trainObsList.size(), 1);
		DecimalFormat percentFormatter = new DecimalFormat("00.00%");
		
		// Training, testing and displaying general accuracy for different values of k
		for(int i=1;i<50;i++){
			classifierTest.setK(i);
			classifierTest.train(trainObsList);
			ArrayList<TestObservation> testObsListLabeledTest = classifierTest.test(testObsList);
			Evaluator evalTest = new Evaluator(testObsListLabeledTest,classifierTest);
			System.out.println("General accuracy for k="+ i +" : "+percentFormatter.format(evalTest.getGeneralAccuracy()));
		}
		
		System.out.println();
		
		// We choose value 1 because it gives us the best accuracy 
		Classifier classifier = new Classifier(28, 10, 2, trainObsList.size(), 1);
		classifier.train(trainObsList);
		ArrayList<TestObservation> testObsListLabeled = classifier.test(testObsList);	
		
		Evaluator eval = new Evaluator(testObsListLabeled,classifier);
		
		System.out.println();
		
		// Displaying accuracy for every number
		for(int i=0;i<10;i++){
			System.out.println("Accuracy for label "+i+" : "+percentFormatter.format(eval.getAccuracy(i)));
		}
		
		System.out.println();
		
		// Displaying Confusion Matrix
		System.out.println("Confusion matrix:");
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
		
		
		// Displaying Highest Confusion Rate Values
		System.out.println("Highset Confusion Rate values:");
		int[][] highestVal = eval.getHighestVal2(4);
		for(int i=0;i<highestVal.length;i++){		
			for(int j=0;j<highestVal[i].length;j++){
				System.out.print(highestVal[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		// Displaying Likelihood maps : Test . Uncomment if needed
		/*for(int i=0;i<10;i++){
			System.out.println("Likelihood Map for label "+i+" : ");
			eval.displayLikelihoodMap(i);
		}*/		
		
		System.out.println();
		
		// Displaying odd ratios maps
		for (int i = 0; i < highestVal.length; i++){
			int label1 = highestVal[i][0];
			int label2 = highestVal[i][1];
			System.out.println("Pair: " + label1 + "," + label2);
			System.out.println("Likelihood Map for label "+label1+" : ");
			eval.displayLikelihoodMap(label1);
			System.out.println("Likelihood Map for label "+label2+" : ");
			eval.displayLikelihoodMap(label2);
			eval.displayOddRatiosMap(label1, label2);
		}
		
		System.out.println();
		
		// Displaying highest and lowest posterior probabilities
		for (int i = 0; i < 10; i++){
			System.out.println();
			System.out.print("Highest posterior probability for label " + i + ":");
			eval.displayHighest(i);
			System.out.println();
			System.out.print("Lowest posterior probability for label " + i + ":");
			eval.displayLowest(i);
		}
		
	}	
}
