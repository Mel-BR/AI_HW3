package digits.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class HelpFuncs {

//	public static void main(String arg[]){
//		ArrayList<TrainObservation> observation = buildTrainObs();
////		for (int i = 0; i < var.length; i++){
////			for (int j = 0; j < var[0].length; j++){
////				System.out.print(var[i][j]);
////			}
////			System.out.println();
////		}
//	}

	// Build for TrainObservation
	public static ArrayList<TrainObservation> buildTrainObs(){
		ArrayList<TrainObservation> ret = new ArrayList<TrainObservation>();
		ArrayList<int[][]> dig = parseDigits("trainingimages");
		ArrayList<Integer> lab = parseLabels("traininglabels");
		
		for (int i = 0; i < dig.size() && i < lab.size(); i++){
			ret.add(new TrainObservation(dig.get(i), lab.get(i)));
			//System.out.println("i: "+i + " val: " +lab.get(i));
		}
		return ret;
	}
	
	// Parses all digits in file: 'text'
	public static ArrayList<int[][]> parseDigits(String text){
		ArrayList<int[][]> ret = new ArrayList<int[][]>();
		int[][] in;
		for(int i = 0; !(isEmpty((in=parseDigit(text, i)))); i++){
			ret.add(in);
			//System.out.println("At char: " + i);
		}
		//System.out.println("Done!");
		return ret;
	}

	// Parses all labels in a file: 'text'
	public static ArrayList<Integer> parseLabels(String text){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int var = 0;
		for (int i = 0; (var=parseLabel(text, i))!=-1; i++)
			ret.add(var);
		return ret;
	}
	
	// Get the 28x28 array of the number at loc in the file text
	// 0 is blank, 1 is '+' or '#'
	public static int [][] parseDigit(String text, int loc){
		int[][] ret = new int[28][28];

		URL res = HelpFuncs.class.getResource("/digits/inputs/"+text);
		FileReader input;
		String nextLine;
		try {
			input = new FileReader(res.getPath());
			BufferedReader bufRead = new BufferedReader(input);
			for (int i = 0; (nextLine=bufRead.readLine()) != null &&(i < loc * 28); i++) {
				//System.out.println("+" +nextLine);
			}
			for (int i = 0; i<28; i++){
				nextLine=bufRead.readLine();
				if (nextLine == null){
					return ret;
				}
				char [] val = nextLine.toCharArray();
				for (int j = 0; j<val.length; j++){
					ret[i][j] = (val[j]=='+' || val[j]=='#')?1:0;
				}

			}
			bufRead.close();
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	// Get the label in file 'text' at location
	public static int parseLabel(String text, int loc){
		URL res = HelpFuncs.class.getResource("/digits/inputs/"+text);
		FileReader input;
		String nextLine = "";
		try {
			input = new FileReader(res.getPath());
			BufferedReader bufRead = new BufferedReader(input);
			for (int i = 0; (nextLine=bufRead.readLine()) != null &&(i < loc); i++) {
			}
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (nextLine==null)?-1:Integer.parseInt(nextLine);
	}
	
	// Checks if an int[][] is all equal to 0
	private static boolean isEmpty(int[][] in){
		for (int i = 0; i<in.length; i++){
			for (int j = 0; j<in[0].length; j++){
				if (in[i][j]!=0){
					return false;
				}
			}
		}
		return true;
	}
}