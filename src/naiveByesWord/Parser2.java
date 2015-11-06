package naiveByesWord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser2 {

	
	
	static public BufferedReader createBufferedReader(String fileName){
		String path = Parser2.class.getResource("resWord/" + fileName).getPath();
		FileReader file_to_read = null;
		try {
			file_to_read = new FileReader(path);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bf = new BufferedReader(file_to_read);
		return bf;
	}
	
	
	static public int fillWordListFromLine(String aLine,LinkedList<Node> wordListTest){
		
		String REGEX = "(\\s)(\\w*)(\\W)(\\d)";
		Pattern p = Pattern.compile(REGEX);
		String INPUT = aLine;
		Matcher m = p.matcher(INPUT); // get a matcher object
//		Map<String, Node> dictionary = new HashMap<String, Node>();

		int type = (int) aLine.charAt(0)-48;	//because of anscii
		if(type == -3){		//if minus sign read next character.
			type = -((int) aLine.charAt(1)-48);
		}
				while(m.find()) {
					String word = m.group(2);
					if (wordListTest.contains(word)==false){
						Node tempNode = new Node(word);
						wordListTest.add(tempNode);
//						dictionary.put(word, tempNode);
						
					}
				}
				
		return type;
	}
	
	
		
	
	
	static public void readData(int[] types, String fileName, Map<String, Node2> dictionary, LinkedList<Node2> wordList, float[] prior){

		BufferedReader bf = createBufferedReader(fileName);
		LinkedList<String> wordListType1 = new LinkedList<String>();
		LinkedList<String> wordListType2 = new LinkedList<String>();

		
		String aLine;
		int[] typeOcc = new int[2];
		int[] nrOfUniqueOccr = new int[2];
		int[] totalNumbers = new int[2];

			try {
				while((aLine = bf.readLine()) != null){
					parseInLine(types, aLine, dictionary, wordList, wordListType1, wordListType2,typeOcc, nrOfUniqueOccr, totalNumbers);
				}
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int nrOfWords = wordList.size();
			for(int i = 0; nrOfWords>i;i++){
				wordList.get(i).calcProbs(nrOfUniqueOccr, totalNumbers);
				wordList.get(i).calcProbs2(typeOcc);
			}
			
			prior[0] = (float) typeOcc[0]/(typeOcc[0]+typeOcc[1]);
			prior[1] = (float) typeOcc[1]/(typeOcc[0]+typeOcc[1]);
			
	}
	
	
	public static void parseInLine(int[] types, String aLine, Map<String, Node2> dictionary, LinkedList<Node2> wordList, LinkedList<String> wordListType1,LinkedList<String> wordListType2,int[] typeOcc, int[] nrOfUniqueOccr, int[]totalNumbers){
		
		String REGEX = "(\\s)(\\w*)(\\W)(\\d)";
		Pattern p = Pattern.compile(REGEX);
		String INPUT = aLine;
		Matcher m = p.matcher(INPUT); // get a matcher object

		int type = (int) aLine.charAt(0)-48;	//because of anscii
		if(type == -3){		//if minus sign read next character.
			type = -((int) aLine.charAt(1)-48);
		}

		if (type == types[0]){
			typeOcc[0]++;
			//TYPE 1
			while(m.find()) {
				String word = m.group(2);
				if (wordListType1.contains(word)){
					dictionary.get(word).occur[0]+= Integer.parseInt(m.group(4));
					totalNumbers[0]+=Integer.parseInt(m.group(4));
					dictionary.get(word).occurPerInstance[0]++;
				}else{
					wordListType1.add(word);
					if(dictionary.containsKey(word)==false){
						Node2 tempNode = new Node2(word);
						dictionary.put(word, tempNode);
						wordList.add(tempNode);
						tempNode.occur[0]+= Integer.parseInt(m.group(4));
						tempNode.occurPerInstance[0]++;
						nrOfUniqueOccr[0]++;
					}
					totalNumbers[0]+=Integer.parseInt(m.group(4));
				}
			}
			

		}else if(type == types[1]){
			typeOcc[1]++;
			//TYPE 2
			while(m.find()) {
				String word = m.group(2);
				if (wordListType2.contains(word)){
					dictionary.get(word).occur[1]+= Integer.parseInt(m.group(4));
					totalNumbers[1]+=Integer.parseInt(m.group(4));
					dictionary.get(word).occurPerInstance[1]++;
				}else{
					wordListType2.add(word);
					if(dictionary.containsKey(word)==false){
						Node2 tempNode = new Node2(word);
						dictionary.put(word, tempNode);
						wordList.add(tempNode);
						tempNode.occur[1]+= Integer.parseInt(m.group(4));
						tempNode.occurPerInstance[1]++;
						nrOfUniqueOccr[1]++;
					}
					totalNumbers[1]+=Integer.parseInt(m.group(4));
				}
			}
			
		}else{
			System.out.println("Not expected type in reading txt: "+type);
		}
	}
	
	

	
}
