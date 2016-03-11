/**
 * 	Welcome to the lab, minion. Henceforth you shall do the bidding of Professor
   	Boolean. Some say he's mad, trying to develop a zombie serum and all... but we
   	think he's brilliant!
   	
	First things first - Minions don't speak English, we speak Minglish. Use the
	Minglish dictionary to learn! The first thing you'll learn is how to use the
	dictionary.
	
	Open the dictionary. Read the page numbers, figure out which pages come before
	others. You recognize the same letters used in English, but the order of letters
	is completely different in Minglish than English (a < b < c < ...).
	
	Given a sorted list of dictionary words (you know they are sorted because you
	can read the page numbers), can you find the alphabetical order of the Minglish
	alphabet?
	
	For example, if the words were ["z", "yx", "yz"] the alphabetical order would be
	"xzy," which means x < z < y. The first two words tell you that z < y, and the
	last two words tell you that x < z.
	
	Write a function answer(words) which, given a list of words sorted
	alphabetically in the Minglish alphabet, outputs a string that contains each
	letter present in the list of words exactly once; the order of the letters in
	the output must follow the order of letters in the Minglish alphabet.
	
	The list will contain at least 1 and no more than 50 words, and each word will
	consist of at least 1 and no more than 50 lowercase letters [a-z].
	It is guaranteed that a total ordering can be developed from the input provided,
	(i.e. given any two distinct letters, you can tell which is greater),
	and so the answer will exist and be unique.
 */

import java.util.ArrayList;

public class MinglishLesson {
	public static void main(String[] args) {
		String[] words = {"gc", "gcb", "gcg", "gg", "eb", "ec", "ef", "ee", "ag", "af", "d"};
		
		System.out.println(answer(words));
	}
	
	/**
	 * Gets the concatenation of two characters at the index in which characters differ for the first time in two strings.
	 * @param s1 The first string
	 * @param s2 The second String
	 * @return The concatenation of two characters at the index in which two strings (s1, s2) differ for the first time. 
	 * Returns empty string if both strings are the same up to the length of the first string
	 */
    public static String getFirstDifference(String s1, String s2) {
		String result = "";
		char ch0 = 0, ch1 = 0;
		int i = 0;
		
		for (i = 0; i < s1.length(); i++) {
			if ((ch0 = s1.charAt(i)) != (ch1 = s2.charAt(i))) {
				result += ch0;
				result += ch1;
				break;		
			}
		
		}
		
		return result;
	}
	
	
	/**
	 * Gets a list of strings made up of two character pairs as they appear in order in the list of words given
	 * These are the clues for the orientation of the alphabet
	 * @param words The list of words from which to extract the list of characters in order
	 * @return A list of characters, depicting which characters come before other characters in the custom alphabet
	 */
	public static ArrayList<String> getCompareList(String[] words) {
		ArrayList<String> list = new ArrayList<String>();
		String in = null;
		
		for (int i = 0; i < words.length - 1; i++) {				
			if ((in = getFirstDifference(words[i], words[i + 1])) != "") {
				list.add(in);
			}
		}
	
		return list;
	}
    
    public static String answer(String[] words) {
		ArrayList<String> list = getCompareList(words);
		boolean swap = true;
		String sorted = "";
		String in = "";
		char ch0 = 0, ch1 = 0;
		int idx0 = 0, idx1 = 0;
		
		while (swap) {
			swap = false;
			
			for (String s : list) {		
				ch0 = s.charAt(0);
				ch1 = s.charAt(1);
				
				in += ch0;
				in += ch1;
				
				idx0 = sorted.indexOf(ch0);
				idx1 = sorted.indexOf(ch1);
				
				//char 0 sorted, char 1 not sorted yet
				if ((idx0 != -1 && idx1 == -1)) {
					sorted = sorted.substring(0, idx0) + in + sorted.substring(idx0 + 1);
					swap = true;
				//char 1 sorted, char 0 not sorted yet
				} else if ((idx1 != -1 && idx0 == -1)) {
					sorted = sorted.substring(0, idx1) + in + sorted.substring(idx1 + 1);
					swap = true;
				//char 0 and 1 are in the wrong order
				} else if (idx0 > idx1) {
					sorted = sorted.substring(0, idx1) + ch0 + sorted.substring(idx1 + 1);
					sorted = sorted.substring(0, idx0) + ch1 + sorted.substring(idx0 + 1);
					swap = true;
				//nothing is sorted yet
				} else if (idx0 == -1 && idx1 == -1) {
					sorted += in;
					swap = true;
				}
				
				in = "";
			}
		}
		
		return sorted;
	}
}
