package digits;

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
		for(int i=0;i<10;i++){
			System.out.println(eval.getAccuracy(i));
		}
		
		
		
		
	}	
}
