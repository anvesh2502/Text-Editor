package spelling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Anvesh
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

	private TrieNode root;
	private int size;
	
	private HashMap<String,String> chatAcronyms;


	public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		chatAcronyms=new HashMap<String,String>();
		chatAcronyms.put("u", "you");
		chatAcronyms.put("2","to");
		
	}


	/** Insert a word into the trie.
	 *Ignoring the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
		if(word==null)
			return false;
		if(word.length()==0)
			return false;
		word=word.toLowerCase();
		if(isWord(word))
			return false;
		char[] cArray=word.toCharArray();
		TrieNode node=root;
		for(int i=0;i<cArray.length;i++)
		{
			char c=cArray[i];
			if(node.contains(c))
				node=node.getChild(c);
			else
				node=node.insert(c);
		}
		node.setEndsWord(true);
		size++;
		return true;
	}
	
	
	

	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		return size;
	}


	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
		if(s==null)
			return false;
		if(s.length()==0)
			return false;
		char[] cArray=s.toLowerCase().toCharArray();
		TrieNode node=root;
		for(int i=0;i<cArray.length;i++)
		{
			char c=cArray[i];
			if(node.contains(c))
				node=node.getChild(c);
			else
				return false;
		}
		return node.endsWord();

	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
	 * in terms of length
	 * If this string is not in the trie, it returns null.
	 * @param text The text to use at the word stem
	 * @param n The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */@Override
	 public List<String> predictCompletions(String prefix, int numCompletions) 
	 {
		 // TODO: Implement this method
		 // This method should implement the following algorithm:
		 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
		 //    empty list
		 // 2. Once the stem is found, perform a breadth first search to generate completions
		 //    using the following algorithm:
		 //    Create a queue (LinkedList) and add the node that completes the stem to the back
		 //       of the list.
		 //    Create a list of completions to return (initially empty)
		 //    While the queue is not empty and you don't have enough completions:
		 //       remove the first Node from the queue
		 //       If it is a word, add it to the completions list
		 //       Add all of its child nodes to the back of the queue
		 // Return the list of completions
       String acronymWord=chatAcronyms.get(prefix);
       if(acronymWord==null)
		 return getLevelList(prefix, numCompletions);
       List<String> list=getLevelList(prefix, numCompletions);
       list.add(0, acronymWord);
       return list;
	 }


	 // For debugging
	 public void printTree()
	 {
		 printNode(root);
	 
	 }
	 /** Do a pre-order traversal from this node down */
	 public void printNode(TrieNode curr)
	 {
		 if (curr == null) 
			 return;

		 System.out.println(curr.getText());

		 TrieNode next = null;
		 for (Character c : curr.getValidNextCharacters()) {
			 next = curr.getChild(c);
			 printNode(next);
		 }
	 }

	 List<String>	getLevelList(String prefix,int num)
	 {
		 boolean wordFlag=false;
		 List<String> suggestions=new ArrayList<String>();
		 if(prefix==null )
			 return suggestions;
		 if(num==0)
			 return suggestions;
		 TrieNode node=root;
		 char[] cArray=prefix.toLowerCase().toCharArray();
		 for(int i=0;i<cArray.length;i++)
		 {
			 
           if(node.contains(cArray[i]))
           {
        	   wordFlag=true;
        	   node=node.getChild(cArray[i]);
           }
           else
        	   break;
		 }
	      if(!wordFlag && prefix.length()!=0)
	    	  return suggestions;
	      if(node.endsWord())
	    	 suggestions.add(node.getText());
         Queue<TrieNode> queue=new LinkedList<TrieNode>();
         for(char c : node.getValidNextCharacters())
         {
        	 queue.add(node.getChild(c));
         }
		 while(!queue.isEmpty() && suggestions.size()<num)
		 {
           TrieNode tn=queue.poll();
           if(tn.endsWord())
           {
        	   suggestions.add(tn.getText());
           }
           for(char c : tn.getValidNextCharacters())
           {
          	 queue.add(tn.getChild(c));
           }
  		   
		 }

		 return suggestions; 
	 }
 
	 


}