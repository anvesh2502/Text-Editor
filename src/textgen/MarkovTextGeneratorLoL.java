package textgen;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author Anvesh
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	private LinkedHashSet<String> keyWords;
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
		keyWords=new LinkedHashSet<String>();
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		if(sourceText.trim().equals(""))
			return;
	 StringTokenizer tokenizer=new StringTokenizer(sourceText," ");
	 while(tokenizer.hasMoreTokens())
	 {
		 keyWords.add(tokenizer.nextToken());
	 }
	 for(String s : keyWords)
	 {
		 ListNode ln=new ListNode(s);
		 wordList.add(ln);
	 }
	 tokenizer=new StringTokenizer(sourceText);
	 int length=tokenizer.countTokens();
	 String word=tokenizer.nextToken();
	 starter=word;
	 for(int i=0;i<length;i++)
	 {
     ListNode l=getNode(word);
     if(!tokenizer.hasMoreTokens())
    	 break;
     String nextWord=tokenizer.nextToken();
     if(!l.contains(nextWord))
     l.addNextWord(nextWord);
     word=nextWord; 
	 }
	 ListNode lastNode=wordList.get(wordList.size()-1);
	 lastNode.addNextWord(starter);
	 
	 
	 
	}
	

	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords)
	{
	if(wordList.size()==0)
		return "";
	String output=""+starter;
	starter=wordList.get(0).getWord();
	String curr=starter;
	ListNode node=getNode(curr);
	while(numWords!=0)
	{
		curr=node.getRandomNextWord(rnGenerator);
		output+=" "+curr;
		numWords--;
	    node=getNode(curr);
	}
		
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
     wordList=new ArrayList<ListNode>();
     keyWords.clear();
     starter="";
     train(sourceText);
      
     
	}
	
	private ListNode getNode(String word)
	{
		for(ListNode ln : wordList)
		{
		 if(ln.getWord().equals(word))
			 return ln;
		}
		return null;
	}
	
	
}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public boolean contains(String word)
	{
	return nextWords.contains(word);	
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	  int index=generator.nextInt(nextWords.size());
	    return nextWords.get(index);
	
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


