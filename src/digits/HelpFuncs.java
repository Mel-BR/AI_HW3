package digits;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HelpFuncs {

	// Get the 28x20 array of the number at loc in the file text
	// 0 is blank, 1 is '+', 2 is '#'
	public int [][] parseDigit(String text, int loc){
		int[][] ret = new int[28][28];
		File file = new File("/AI_HW3/src/digits/inputs/" + text);
		try {
			Scanner input = new Scanner(file);
			int counter = 0;
			while(input.hasNext()) {
				String nextLine = input.nextLine();
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}