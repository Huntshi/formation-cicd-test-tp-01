package com.devops.cicd;

import org.junit.jupiter.api.Test;

import static java.lang.System.load;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        // Assert
        double amountExcl = 100.0;
        boolean vip = true;
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig config = loader.load();
        PricingService service = new PricingService(config);

        // Act
        double actual = service.finalTotal(amountExcl, vip);
        double afterVat = amountExcl * (1.0 + config.getVatRate()) / 100.0;
        double afterVip = vip ? afterVat * 0.90 : afterVat;
        double shipping = afterVip < config.getFreeShippingThreshold() ? 4.99 : 0.0;
        double expected = afterVip + shipping;

        // Verify
        assertEquals(expected, actual);
    }
}
