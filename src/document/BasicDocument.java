package document;

import java.util.List;


public class BasicDocument extends Document 
{
	/* 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		//TODO: Implement this method.  See the Module 1 support videos 
	    // if you need help.
	    return getTokens("[a-zA-Z]+").size();
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
	public int getNumSentences()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help.
        return getTokens("[^.!?]+").size();
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
	public int getNumSyllables()
	{
	    //TODO: Implement this method.  See the Module 1 support videos 
        // if you need help.
		List<String> l=getTokens("[a-zA-Z]+");
		int count=0;
		for(int i=0;i<l.size();i++)
		{
		count+=countSyllables(l.get(i));
		}
		return count;
		
	}
	
	
	
	
	public int countSyllables(String word) {
	    int      syl    = 0;
	    boolean  vowel  = false;
	    int      length = word.length();

	    for(int i=0; i<length-1; i++) {
	      if        (isVowel(word.charAt(i)) && (vowel==false)) {
	        vowel = true;
	        syl++;
	      } else if (isVowel(word.charAt(i)) && (vowel==true)) {
	        vowel = true;
	      } else {
	        vowel = false;
	      }
	    }

	    char tempChar = word.charAt(word.length()-1);
	    if(isVowel(tempChar) && tempChar!='e' && !vowel)
	    syl++;
	    if(tempChar=='e' && syl==0)
	    	syl=1;
	    
	    return syl;
	}
 
	public static boolean isVowel(char c) {
	    if      ((c == 'a') || (c == 'A')) { return true;  }
	    else if ((c == 'e') || (c == 'E')) { return true;  }
	    else if ((c == 'i') || (c == 'I')) { return true;  }
	    else if ((c == 'o') || (c == 'O')) { return true;  }
	    else if ((c == 'u') || (c == 'U')) { return true;  }
	    else if ((c == 'y') || (c == 'Y')) { return true;  }
	    else                               { return false; }
	  }
	
	
}
