package refurbishedTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Translator {
	
	static String firstLanguage = "English.txt";		//Default language to translate
	static String secondLanguage = "Spanish.txt";
	
	public void languageChoice() {
		Scanner scan = new Scanner(System.in);
		String language1;
		String language2;
		String[] languageArray = {"Spanish", "English", "French", "Dutch", "German"};
		List<String> languageList = new ArrayList<>(Arrays.asList(languageArray));
		boolean valid = false;
		
		
		do {
			System.out.println("What language would you like to translate from");
			language1 = scan.nextLine();
			
			if(languageList.contains(language1))
			{
				valid = true;
			}
			
		}while(!valid); 
		
		do {
			System.out.println("What language would you like to translate to");
			language2 = scan.nextLine();
			
			if(languageList.contains(language2)) {
				valid = true;
			}else if(languageList.contains(language2)){
				valid = false;
			}	
		}while(!valid);
		
		firstLanguage = "Languages/" + language1 + ".txt";
		secondLanguage = "Languages/" + language2 + ".txt";
		
	}
	
	public void translatePhrase(Map<Integer, String> secondLanguageHashMap, int[] keys) {
			
			String translation = "";
			String tempTranslation[] = new String[keys.length];
			
			
			for(int i = 0; i < keys.length; i++) 
			{
	       	 String tempString = secondLanguageHashMap.get(keys[i]);
	       	 tempTranslation[i] = tempString;       	
			}
	       
	       for(int i = 0; i < tempTranslation.length; i++) 
	       {
	       	 translation = translation + " " + tempTranslation[i];
	       }
	       
	       System.out.println(translation);
	       
		}
	
	public String[] stringToArray() {
		Scanner scan = new Scanner(System.in);
		String phrase;
		
		System.out.println("What phrase would you like to translate");
		phrase = scan.nextLine();
		
    	String[] phraseArray = phrase.split(" ");
    	
    	for (int i = 0; i < phraseArray.length; i++) {
    		phraseArray[i] = phraseArray[i].replaceAll("[^\\w]", "");
    	}
    	
    	return phraseArray;
	}
	
	public void textFileToArray(Map<String, Integer> firstLanguageHashMap,Map<Integer, String> secondLanguageHashMap) {
		System.out.println("Change the contents of textfile.txt for different out come!");
		String file_name = "textfile.txt";
		int wordCount = 0;
		float wps = 0;
		Date time_joined = new Date();
		BufferedReader br=null ;
		
		String line;
		try {
			File file = new File("textfile.txt");
            br = new BufferedReader(new FileReader(file));
            
			
			line = br.readLine();
			
			while(line!=null) {
				
				String phrase;	
				phrase = line;
				
		    	String[] phraseArray = phrase.split(" ");
		    	
		    	for (int i = 0; i < phraseArray.length; i++) {
		    		phraseArray[i] = phraseArray[i].replaceAll("[^\\w]", "");
		    	}
		    	
		    	
		    	int[] keys = this.getKeys(phraseArray, firstLanguageHashMap, secondLanguageHashMap);
		    	this.translatePhrase(secondLanguageHashMap, keys);
		    	wordCount = wordCount + phraseArray.length;
		    	
				//System.out.println(line);
				line = br.readLine();
			}
			
		}
		catch(Exception e){
			System.out.println("Error : " + e.getMessage());
		}
		finally {
			if(br!=null) {
				try {
	                br.close();
	            }catch(Exception e){};
			}
		}
		wps = getWordsPerSecond(wordCount, time_joined); //Calls method to get time from when the method started to when it finished
		System.out.println("This was translated with " + wps + "w/s");
		
	}
	
	/**
	 * Calculates words per second
	 * @param wordCount
	 * @param time_joined
	 * @return wps
	 */
	public float getWordsPerSecond(int wordCount, Date time_joined)
    {
        Date time_now = new Date();
        float elapsed;
        float wps;
        
        elapsed = time_now.getTime() - time_joined.getTime();   
        
        wps = wordCount / (elapsed / 1000);
        
        return wps;
    }
	
	public int[] getKeys(String[] phraseArray, Map<String, Integer> firstLanguageHashMap, Map<Integer, String> secondLanguageHashMap) {
		int keys[] = new int[phraseArray.length];
		int tempNum = 0;
		
		//If its 1 word, it gets back null AND use custom is on	
		if(phraseArray.length == 1 && firstLanguageHashMap.get(1) == null) 
		{	
				tempNum = 10001;
				keys[0] = tempNum;			
				return keys;
		} else {
			for(int i = 0; i < phraseArray.length; i++) 
			{   	
				if(firstLanguageHashMap.get(phraseArray[i]) == null) 
				{	
					tempNum = 10001;		//If the word cant be found then set it to 10001 (unavailableWord)
					keys[i] = tempNum;
				} else {
					tempNum = firstLanguageHashMap.get(phraseArray[i]);	//If it can be found then set the value of the found integer to tempNum
        			keys[i] = tempNum;		//tempNum is then set to keys[i]
				}
        	}

			translatePhrase(secondLanguageHashMap, keys);	//Calls the translate method to finally finish the translation
			return keys;
		}
	}
	
	public static Map<String, Integer> firstLanguageToHashMap(){
        
    	Map<String, Integer> mapFileContents = new LinkedHashMap<String, Integer>();
        BufferedReader br = null;
        try{
            
            File file = new File(firstLanguage);
            br = new BufferedReader(new FileReader(file));
            
            String line = null;
            while ((line = br.readLine()) != null){
                
                String[] parts = line.split(":");
            
            String word = parts[0].trim();
            Integer id = Integer.parseInt( parts[1].trim() );

            if(!word.equals("") && !id.equals(""))
                mapFileContents.put(word, id);
        }
              
    }catch(Exception e){
        e.printStackTrace();
    }finally{
        
        if(br != null) {
            try {
                br.close();
            }catch(Exception e){};
        }
    } 
    return mapFileContents;

}

	/**
	 * Loads the second language HashMap
	 * @return mapFileContents
	 */
	public static Map<Integer, String> secondLanguageToHashMap(){
	    
		Map<Integer, String> mapFileContents = new LinkedHashMap<Integer, String>();
	    BufferedReader br = null;
	    
	    try{
	        
	        File file = new File(secondLanguage);
	        br = new BufferedReader(new FileReader(file));
	        
	        		
	        String line = null;
	        while ((line = br.readLine()) != null){
	        	
	            String[] parts = line.split(":");
	        
	        String word = parts[0].trim();
	        Integer id = Integer.parseInt( parts[1].trim() );
	        
	        
	        if(!word.equals("") && !id.equals(""))
	            mapFileContents.put(id, word);
	    }
	                
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    
	    if(br != null) {
	        try {
	            br.close();
	        }catch(Exception e){};
	    }
	}
	
	return mapFileContents;
	
	}
	
	
	
	//NEW CODE
	
	public static Map<String, Integer> firstLanguageToHashMap1(String language){
	        
	    	Map<String, Integer> mapFileContents = new LinkedHashMap<String, Integer>();
	        BufferedReader br = null;
	        try{
	            
	            File file = new File("Languages/" + language + ".txt");
	            br = new BufferedReader(new FileReader(file));
	            
	            String line = null;
	            while ((line = br.readLine()) != null){
	                
	                String[] parts = line.split(":");
	            
	            String word = parts[0].trim();
	            Integer id = Integer.parseInt( parts[1].trim() );
	
	            if(!word.equals("") && !id.equals(""))
	                mapFileContents.put(word, id);
	        }
	              
	    }catch(Exception e){
	        e.printStackTrace();
	    }finally{
	        
	        if(br != null) {
	            try {
	                br.close();
	            }catch(Exception e){};
	        }
	    } 
	    return mapFileContents;
	
	}

	public static Map<Integer, String> secondLanguageToHashMap1(String language){
	    
		Map<Integer, String> mapFileContents = new LinkedHashMap<Integer, String>();
	    BufferedReader br = null;
	    
	    try{
	        
	        File file = new File("Languages/" + language + ".txt");
	        br = new BufferedReader(new FileReader(file));
	        
	        		
	        String line = null;
	        while ((line = br.readLine()) != null){
	        	
	            String[] parts = line.split(":");
	        
	        String word = parts[0].trim();
	        Integer id = Integer.parseInt( parts[1].trim() );
	        
	        
	        if(!word.equals("") && !id.equals(""))
	            mapFileContents.put(id, word);
	    }
	                
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    
	    if(br != null) {
	        try {
	            br.close();
	        }catch(Exception e){};
	    }
	}

	    return mapFileContents;

	}
	
	public String[] stringToArray1(String phrase) {
		Scanner scan = new Scanner(System.in);
		
    	String[] phraseArray = phrase.split(" ");
    	
    	for (int i = 0; i < phraseArray.length; i++) {
    		phraseArray[i] = phraseArray[i].replaceAll("[^\\w]", "");
    	}
    	
    	return phraseArray;
	}
	
	public int[] getKeys1(String[] phraseArray, Map<String, Integer> firstLanguageHashMap, Map<Integer, String> secondLanguageHashMap) {
		int keys[] = new int[phraseArray.length];
		int tempNum = 0;
		
		//If its 1 word, it gets back null AND use custom is on	
//		if(phraseArray.length == 1 && firstLanguageHashMap.get(1) == null) 
//		{	
//				tempNum = 10001;
//				keys[0] = tempNum;
//				return keys;
//		} else {
		for(int i = 0; i < phraseArray.length; i++) 
		{   	
			if(firstLanguageHashMap.get(phraseArray[i]) == null) 
			{	
				tempNum = 10001;		//If the word cant be found then set it to 10001 (unavailableWord)
				keys[i] = tempNum;
			} else {
				tempNum = firstLanguageHashMap.get(phraseArray[i]);	//If it can be found then set the value of the found integer to tempNum
    			keys[i] = tempNum;		//tempNum is then set to keys[i]
			}
    	}

			//translatePhrase(secondLanguageHashMap, keys);	//Calls the translate method to finally finish the translation
			return keys;
	}
	
	public String translatePhrase1(Map<Integer, String> secondLanguageHashMap, int[] keys) {
		
		String translation = "";
		String tempTranslation[] = new String[keys.length];
		
		
		for(int i = 0; i < keys.length; i++) 
		{
       	 String tempString = secondLanguageHashMap.get(keys[i]);
       	 tempTranslation[i] = tempString;       	
		}
       
       for(int i = 0; i < tempTranslation.length; i++) 
       {
       	 translation = translation + " " + tempTranslation[i];
       }
       
       return translation;
	}
	
}
