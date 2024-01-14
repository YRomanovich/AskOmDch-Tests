package org.askOmDch.factory.abstractFactory;

import org.askOmDch.constants.DriverType;
import org.askOmDch.factory.ChromeDriverManager;
import org.askOmDch.factory.DriverManagerInterface;
import org.askOmDch.factory.FIreFoxDriverManager;

public class DriverManagerFactoryAbstract {
    public static DriverManagerAbstract getManager(DriverType driverType){
        switch (driverType){
            case CHROME -> {
                return new ChromeDriverManagerAbstract();
            }
            case FIREFOX -> {
                return new FireFoxDriverManagerAbstract();
            }
            default -> throw new IllegalStateException("Invalid driver: " + driverType);
        }
    }
}
