package com.example;

import java.util.Comparator;

public class HighScoreComparator implements Comparator<String>{
	public int compare(String o1, String o2) {
		if (Integer.valueOf(o1.split(" ")[0]) > Integer.valueOf(o2.split(" ")[0])) {
			return -1;
	    } else if (Integer.valueOf(o1.split(" ")[0]) < Integer.valueOf(o2.split(" ")[0])) {
	    	return 1;
	    } else {
	    	return 0;
	    }
	  }
}
