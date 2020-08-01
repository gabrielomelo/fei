package Util;

import java.text.SimpleDateFormat;

/**
 * Classe que manipulada data e hora.
 * @author Gabriel Melo
 */
public class TimeAdapter {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String HOUR_FORMAT = "h:mm - a";
    
    public static String getCurrentHour() {
        java.util.Date now = new java.util.Date();
        SimpleDateFormat locale = new SimpleDateFormat(TimeAdapter.HOUR_FORMAT);
        return locale.format(now);
    }
    
    public static String getCurrentDate() {
        java.util.Date now = new java.util.Date();
	SimpleDateFormat locale = new SimpleDateFormat(TimeAdapter.DATE_FORMAT);
        return locale.format(now);
    }
    
    public static boolean compareCurrentHour(String hour) {
        SimpleDateFormat locale = new SimpleDateFormat(TimeAdapter.HOUR_FORMAT);
        return ((locale.format(new java.util.Date())).equals(hour));
    }
    
    public static boolean compareCurrentDate(String date) {
        SimpleDateFormat locale = new SimpleDateFormat(TimeAdapter.DATE_FORMAT);
        return ((locale.format(new java.util.Date())).equals(date));
    }
}
