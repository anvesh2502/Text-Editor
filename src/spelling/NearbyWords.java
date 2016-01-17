/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Anvesh
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	private static final int THRESHOLD = 1000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   subsitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);

		   return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void subsitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) 
	{
		for(int index = 0; index <=s.length(); index++)
		{
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) 
			{
		      char c=(char) charCode;
		      String newString=s.substring(0,index)+c+s.substring(index);
		      if(wordsOnly)
		      {
		    	  if(dict.isWord(newString))
				      currentList.add(newString);
		      }
		      else
		      currentList.add(newString);
			}
		}

	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) 
	{
	int start=0,end=s.length();
			
	for(int i=0;i<s.length();i++)
	{
	String newString=s.substring(start,i)+s.substring(i+1,end);
	if(wordsOnly)
	{
	if(dict.isWord(newString))
		currentList.add(newString);
	}
	else
		currentList.add(newString);
	}

	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) 
	{
		List<String> suggestions=new ArrayList<String>();
		HashMap<String,List<String>> wordMap=new HashMap<String,List<String>>();
		HashSet<String> visited=new HashSet<String>();
		Queue<String> queue=new LinkedList<String>();
		List<String> list=new ArrayList<String>();
		queue.add(word);
		for(int i=0,limit=0;i<numSuggestions && !queue.isEmpty();limit++)
		{
			if(limit==THRESHOLD )
				return suggestions;
			else if(suggestions.size()==0)
				limit=0;
				
			String w=queue.poll();
			if(!wordMap.containsKey(w))
			{
			  list=distanceOne(w, false);
			  wordMap.put(w, list);
			}
			else
				list=wordMap.get(w);
			for(String s : list)
			{
				if(dict.isWord(s) && !visited.contains(s) )
				{
					suggestions.add(s);
					i++;
				}
				else
				queue.add(s);	
			 visited.add(s);
			}
			
		}
		System.out.println(wordMap.size());
		return suggestions;
		
	}	


}
