package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    public PricingConfig load() {
        double vatRate = 0;
        double shipping = 0;

        try(InputStream input = PricingConfigLoader.class.getClassLoader().getResourceAsStream("app.properties")){
            Properties prop = new Properties();
            prop.load(input);
            vatRate = Double.parseDouble(prop.getProperty("vatRate"));
            shipping = Double.parseDouble(prop.getProperty("shipping"));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        PricingConfig file = new PricingConfig(vatRate, shipping);

        return file;
    }

    private String required(Properties props, String key) {
         return "yes";
    }
}
