package org.askOmDch.factory;

import org.askOmDch.constants.DriverType;

public class DriverManagerFactory {

    public static DriverManagerInterface getManager(DriverType driverType){
        switch (driverType){
            case CHROME -> {
                return new ChromeDriverManager();
            }
            case FIREFOX -> {
                return new FIreFoxDriverManager();
            }
            default -> throw new IllegalStateException("Invalid driver: " + driverType);
        }
    }
}
