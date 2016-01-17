package document;

import java.util.List;

/** 
 * A class that represents a text document
 * It does one pass through the document to count the number of syllables, words, 
 * and sentences and then stores those values.
 * 
 * 
 */
public class EfficientDocument extends Document {

	private int numWords;  // The number of words in the document
	private int numSentences;  // The number of sentences in the document
	private int numSyllables;  // The number of syllables in the document
	
	public EfficientDocument(String text)
	{
		super(text);
		processText();
	}
	
	
	/** Return true if this string is a word (as opposed to punctuation)
	 * @param tok The string to check
	 * @return true if tok is a word, false otherwise. */
	private boolean isWord(String tok)
	{
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}
	
	
    /** Passes through the text one time to count the number of words, syllables and 
     * sentences, and set the member variables appropriately.
     * Words, sentences and syllables are defined as described below. 
     */
	private void processText()
	{
		// Provide this first line in the starter code.  
		// Words are only strings of letters.  No numbers.
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		System.out.println(tokens);
		String t="";
		for(int i=0;i<tokens.size();i++)
		{
		t=tokens.get(i);
		if(isWord(t))
			numWords++;
		numSyllables+=countSyllables(t);
		if(t.startsWith(",") || t.startsWith("?") || t.startsWith("!") || t.startsWith("."))
			numSentences++;
		}
		if(!t.startsWith(",") && !t.startsWith("?") && !t.startsWith("!") && !t.startsWith(".") && numWords!=0)
			numSentences++;
		

	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords() {
	    return numWords;
	}

	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences() {
        return numSentences;
	}

	/**
	 * Get the number of syllables in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for a lone "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables() {
        return numSyllables;
	}
	

}
