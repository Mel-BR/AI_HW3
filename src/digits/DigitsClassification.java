package digits;

import java.util.ArrayList;

import digits.entities.Classifier;
import digits.entities.HelpFuncs;
import digits.entities.TestObservation;
import digits.entities.TrainObservation;

public class DigitsClassification {

	public static void main(String arg[]){
		
		System.out.println("Reading training observations...");
		ArrayList<TrainObservation> trainObsList = HelpFuncs.buildTrainObs();		
		
		System.out.println("Reading test observations...");
		ArrayList<TestObservation> testObsList = HelpFuncs.buildTestObs();
		
		System.out.println("Done");
		
		Classifier classifier = new Classifier(28, 10, 2, trainObsList.size(), 1);
		classifier.train(trainObsList);
		ArrayList<TestObservation> testObsListComplete = classifier.test(testObsList);
		
		System.out.println();
		
		
		for(TestObservation testObs : testObsListComplete){
			System.out.println(testObs.getRealLabel()+" "+testObs.getPredictedLabel());
		}
		
		
	}	
}
