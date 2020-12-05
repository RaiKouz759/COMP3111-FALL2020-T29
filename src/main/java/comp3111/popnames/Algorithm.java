package comp3111.popnames;

/**
 * This class deals mainly with Task 4 and Tack 5 and contains only static functions algorithms that could used for the
 * recommendation based tasks.
 * 
 * @author Amrutavarsh
 *
 */
public class Algorithm {
	// Function to calculate the 
	// Jaro Similarity of two Strings 
	
	/**
	 * Determines string similarity given two strings. 
	 * 
	 * @param s1 the first string to be checked
	 * @param s2 the string to be checked against
	 * @return normalized value ranging from 0 to 1 based on similarity of strings
	 */
	static double jaro_distance(String s1, String s2) 
	{ 
		if (s1 == s2) 
			return 1.0; 
	  
		int len1 = s1.length(), 
			len2 = s2.length(); 
	  
		int max_dist = (int) (Math.floor(Math.max(len1, len2) / 2) - 1); 
	  
		int match = 0; 
	  
		int hash_s1[] = new int[s1.length()]; 
		int hash_s2[] = new int[s2.length()]; 
	  
		for (int i = 0; i < len1; i++)  
		{ 
	  
			for (int j = Math.max(0, i - max_dist); 
				j < Math.min(len2, i + max_dist + 1); j++) 
	  
				if (s1.charAt(i) == s2.charAt(j) && hash_s2[j] == 0)  
				{ 
					hash_s1[i] = 1; 
					hash_s2[j] = 1; 
					match++; 
					break; 
				} 
		} 
	  
		if (match == 0) 
			return 0.0; 
		double t = 0; 
	  
		int point = 0; 
		for (int i = 0; i < len1; i++) 
			if (hash_s1[i] == 1) 
			{ 
				while (hash_s2[point] == 0) 
					point++; 
	  
				if (s1.charAt(i) != s2.charAt(point++) ) 
					t++; 
			} 
	  
		t /= 2; 
	  
		// Return the Jaro Similarity 
		return (((double)match) / ((double)len1) 
				+ ((double)match) / ((double)len2) 
				+ ((double)match - t) / ((double)match)) 
			/ 3.0; 
	}
}
