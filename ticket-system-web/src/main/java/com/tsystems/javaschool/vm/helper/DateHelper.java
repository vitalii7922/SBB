package com.tsystems.javaschool.vm.helper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Component
public class DateHelper {
    public DateTime parseBSDate(String dateString, TimeZone timeZone) {
        List<Integer> date = new ArrayList<Integer>(3);
        for (String s : dateString.split("-")) {
            date.add(Integer.parseInt(s));
        }
        return new DateTime(date.get(0), date.get(1), date.get(2), 23, 59, DateTimeZone.forTimeZone(timeZone));
    }

    public DateTime parseBSDateTime(String dateString, String timeString, TimeZone timeZone) {
        List<Integer> datetime = new ArrayList<Integer>(5);
        for (String s : dateString.split("-")) {
            datetime.add(Integer.parseInt(s));
        }
        for (String s : timeString.split(":")) {
            datetime.add(Integer.parseInt(s));
        }
        return new DateTime(datetime.get(0), datetime.get(1), datetime.get(2),
                datetime.get(3), datetime.get(4), DateTimeZone.forTimeZone(timeZone));
    }
}
