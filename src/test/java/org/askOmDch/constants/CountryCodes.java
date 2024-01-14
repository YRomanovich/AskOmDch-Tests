package org.askOmDch.constants;

import java.util.Map;

public class CountryCodes {

    public static String getCountryCode(String countryName){
        Map<String,  String> countryMap = Map.of(
                "Germany", "DE",
                "United States", "US"
        );
        return countryMap.get(countryName);
    }

    public static String getStateCode(String stateName){
        Map<String,  String> countryMap = Map.of(
                "Hamburg", "HH",
                "Texas", "TX"
        );
        return countryMap.get(stateName);
    }
}
