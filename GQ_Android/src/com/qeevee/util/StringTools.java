package com.qeevee.util;

import java.util.Locale;

public class StringTools {

	public static String caseUp(String original) {
		return original.substring(0, 1).toUpperCase(Locale.ENGLISH)
				+ original.substring(1);
	}
	
	public static String trim(String original) {
	    return original.replaceAll("\\s+",
		    " ").trim();
	}

}
