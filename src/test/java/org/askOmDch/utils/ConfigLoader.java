package org.askOmDch.utils;

import org.askOmDch.constants.EnvironmentType;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        String env = System.getProperty("env", String.valueOf(EnvironmentType.STAGING));

        switch (EnvironmentType.valueOf(env)) {
            case STAGING -> properties = PropertyUtils.
                    propertiesLoader("src/test/resources/staging.properties");
            case PRODUCTION -> properties = PropertyUtils.
                    propertiesLoader("src/test/resources/production.properties");
            default -> throw new IllegalStateException("Invalid environment type " + env);
        }
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        String  prop = properties.getProperty("baseUrl");
        if(prop != null) return prop;
        else throw new RuntimeException("Property baseUrl is not specified in the staging.properties file");
    }

    public String getUsername(){
        String  prop = properties.getProperty("username");
        if(prop != null) return prop;
        else throw new RuntimeException("Property username is not specified in the staging.properties file");
    }

    public String getPassword(){
        String  prop = properties.getProperty("password");
        if(prop != null) return prop;
        else throw new RuntimeException("Property password is not specified in the staging.properties file");
    }

    public String getEmail(){
        String  prop = properties.getProperty("email");
        if(prop != null) return prop;
        else throw new RuntimeException("Property email is not specified in the staging.properties file");
    }
}
