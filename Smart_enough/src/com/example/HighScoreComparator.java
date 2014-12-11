package com.example;

import java.util.Comparator;

public class HighScoreComparator implements Comparator<String>{
	public int compare(String o1, String o2) {
	    System.out.println("Compare:" + o1.split(" ")[0] + " i " + o2.split(" ")[0]);
		if (Integer.valueOf(o1.split(" ")[0]) > Integer.valueOf(o2.split(" ")[0])) {
			return -1;
	    } else if (o1.length() < o2.length()) {
	    	return 1;
	    } else {
	    	return 0;
	    }
	  }
}
