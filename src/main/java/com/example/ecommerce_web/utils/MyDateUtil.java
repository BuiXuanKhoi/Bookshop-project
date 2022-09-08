package com.example.ecommerce_web.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyDateUtil {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    public String getStringDate(Date date){
        return dateFormat.format(date);
    }

    public String getStringTime(Date date){
        return timeFormat.format(date);
    }
}
