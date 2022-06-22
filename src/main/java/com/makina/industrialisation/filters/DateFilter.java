package com.makina.industrialisation.filters;

import java.nio.file.attribute.FileTime;
import java.util.concurrent.TimeUnit;

public class DateFilter {

	private DateFilter() {
	    throw new IllegalStateException("DateFilter Utility class");
	}
	
	private static int approximatifMinut = 2;
	
	/**
	 * Indique si les 2 dates fournis sont proches.
	 * @param ft
	 * @param ftToCompare
	 * @return
	 */
	public static boolean isApproximateDate(FileTime ft, FileTime ftToCompare) {
		
		if(ft.compareTo(ftToCompare) == 0) {
			return true;
		} else {
			long ftMinut = TimeUnit.MILLISECONDS.toMinutes(ft.toMillis());
			long ftToCompareMinut = TimeUnit.MILLISECONDS.toMinutes(ftToCompare.toMillis());
			
			if(isApproximateDate(ftMinut, ftToCompareMinut)) {
				return true;
			} else {
				return isApproximateDate(ftToCompareMinut, ftMinut);
			}		
		}		
	}
	
	private static boolean isApproximateDate(long min1, long min2) {
		return (min1 - min2 > 0 && min1 - min2 <= approximatifMinut);
	}
}
