package com.devops.cicd;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PricingConfigLoader {

    public PricingConfig load() {
        Properties prop = new Properties();

        try(InputStream input = PricingConfigLoader.class.getClassLoader().getResourceAsStream("app.properties")){

            if (input == null){
                throw new IllegalStateException("Fichier app.properties introuvale dans le classpath");
            }

            prop.load(input);

        } catch (IOException ex){
            ex.printStackTrace();
        }

        double vatRate = Double.parseDouble(required(prop, "vatRate"));
        double shipping = Double.parseDouble(required(prop, "freeShippingThreshold"));
        PricingConfig file = new PricingConfig(vatRate, shipping);

        return file;
    }

    private String required(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Propriété requise manquante : " + key);
        }
        return value;
    }
}
