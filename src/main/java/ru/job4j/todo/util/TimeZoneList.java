package ru.job4j.todo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class TimeZoneList {
    public static List<String> getTimeZone() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones.stream()
                .map(TimeZone::getID)
                .collect(Collectors.toList());
    }

    public static String getDefaultTimeZone() {
        var defTz = TimeZone.getDefault();
        return defTz.getID();
    }
}
