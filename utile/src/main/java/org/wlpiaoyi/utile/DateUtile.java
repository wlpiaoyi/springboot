package org.wlpiaoyi.utile;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtile {


    public static String format(Date date, String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return  dateFormat.format(date);
    }



}
