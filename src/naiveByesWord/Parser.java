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

public class Parser {

	
	
	static public BufferedReader createBufferedReader(String fileName){
		String path = Parser.class.getResource("resWord/" + fileName).getPath();
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

		int type = Character.valueOf(aLine.charAt(0))-48; //Because ASCII
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
	
	
		
	
	
	static public void readData(int[] types, String fileName, Map<String, Node> dictionaryType1, Map<String, Node> dictionaryType2, LinkedList<Node> wordListType1,LinkedList<Node> wordListType2, float[] prior){

		BufferedReader bf = createBufferedReader(fileName);
		
		String aLine;
		int[] typeOcc = new int[2];
		int[] nrOfUniqueOccr = new int[2];
		int[] totalNumbers = new int[2];
		int ii=0;
			try {
				while((aLine = bf.readLine()) != null){
					ii++;
					parseInLine(types, aLine, dictionaryType1, dictionaryType2, wordListType1, wordListType2,typeOcc, nrOfUniqueOccr, totalNumbers);
				}
				bf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int nrOfWords = wordListType1.size();
			for(int i = 0; nrOfWords>i;i++){
				wordListType1.get(i).calcProbs(nrOfUniqueOccr[0], totalNumbers[0]);
			}
			nrOfWords = wordListType2.size();
			for(int i = 0; nrOfWords>i;i++){
				wordListType2.get(i).calcProbs(nrOfUniqueOccr[1], totalNumbers[1]);
			}
			
			prior[0] = (float) typeOcc[0]/(typeOcc[0]+typeOcc[1]);
			prior[1] = (float) typeOcc[1]/(typeOcc[0]+typeOcc[1]);
			
			
	}
	
	
	public static void parseInLine(int[] types, String aLine, Map<String, Node> dictionaryType1, Map<String, Node> dictionaryType2, LinkedList<Node> wordListType1,LinkedList<Node> wordListType2,int[] typeOcc, int[] nrOfUniqueOccr, int[]totalNumbers){
		
		String REGEX = "(\\s)(\\w*)(\\W)(\\d)";
		Pattern p = Pattern.compile(REGEX);
		String INPUT = aLine;
		Matcher m = p.matcher(INPUT); // get a matcher object
		int type = Character.valueOf(aLine.charAt(0))-48; //Because ASCII

		if (type == types[0]){
			typeOcc[0]++;
			//TYPE 1
			while(m.find()) {
				String word = m.group(2);
				if (dictionaryType1.containsKey(word)){
					dictionaryType1.get(word).occur+= Integer.parseInt(m.group(4));
					totalNumbers[0]+=Integer.parseInt(m.group(4));
				}else{
					Node tempNode = new Node(word);
					wordListType1.add(tempNode);
					dictionaryType1.put(word, tempNode);
					tempNode.occur+= Integer.parseInt(m.group(4));
					totalNumbers[0]+=Integer.parseInt(m.group(4));
					nrOfUniqueOccr[0]++;
				}
			}
			

		}else if(type == types[1]){
			typeOcc[1]++;
			//TYPE 2
			while(m.find()) {
				String word = m.group(2);
				if (dictionaryType2.containsKey(word)){
					dictionaryType2.get(word).occur+= Integer.parseInt(m.group(4));
					totalNumbers[1]+=Integer.parseInt(m.group(4));
				}else{
					Node tempNode = new Node(word);
					wordListType2.add(tempNode);
					dictionaryType2.put(word, tempNode);
					tempNode.occur+= Integer.parseInt(m.group(4));
					totalNumbers[1]+=Integer.parseInt(m.group(4));
					nrOfUniqueOccr[1]++;

				}
			}
			
		}
	}
	
	

	
}
