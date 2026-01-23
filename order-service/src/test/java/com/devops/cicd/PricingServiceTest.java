package com.devops.cicd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingConfig fakeConfig;
    private PricingService service;

    @BeforeEach
    void setUp() {
        fakeConfig = new PricingConfig(20.0, 50.0); // vatRate en pourcentage (20.0 -> 20%)
        service = new PricingService(fakeConfig); // freeShippingThreshold = 50.0
    }

    @Test
    void applyVat_should_return_amount_with_vat(){
        // Assert
        double amountExcl = 100.0;

        // Act
        double expected = amountExcl * (1.0 + fakeConfig.getVatRate() / 100.0); // 100 * 1.20 = 120.0
        double actual = service.applyVat(amountExcl);

        // Verify
        assertEquals(expected,actual);
    }

    @Test
    void applyVipDiscount_should_apply_10_percent_when_vip_true_and_no_discount_when_false(){
        // Assert
        double amount = 120.00;
        double expectedVip = amount * 0.90; // 10% de remise
        double expectedNonVip = amount;

        // Act
        double actualVip = service.applyVipDiscount(amount,true);
        double actualNonVip = service.applyVipDiscount(amount,false);

        // Verify
        assertEquals(expectedVip, actualVip,"applyVipDiscount doit appliquer -10% pour un VIP");
        assertEquals(expectedNonVip, actualNonVip, "applyVipDiscount ne doit rien changer si non-VIP");
    }

    @Test
    void shippingCost_should_return_free_over_threshold_and_fixed_below() {
        // Au-dessus du seuil -> pas de frais
        assertEquals(0.0, service.shippingCost(60.0), 1e-6, "Pas de frais au-dessus du seuil de livraison gratuite");

        // En dessous du seuil -> frais fixes 4.99
        assertEquals(4.99, service.shippingCost(49.99), 1e-6, "Frais de port applicables si montant < seuil");
    }

    @Test
    void finalTotal_should_apply_vat_then_vip_discount_then_shipping() {
        // Assert
        double amountExcl = 100.0;
        boolean vip = true;

        // Act
        double afterVat = amountExcl * (1.0 + fakeConfig.getVatRate() / 100.0); // 100 -> 120
        double afterVip = afterVat * (vip ? 0.90 : 1.0); // VIP : 120 -> 108
        double shipping = afterVip < fakeConfig.getFreeShippingThreshold() ? 4.99 : 0.0; // seuil 50
        double expected = afterVip + shipping; // 108 + 0 = 108
        double actual = service.finalTotal(amountExcl, vip);

        // Verify
        assertEquals(expected, actual);

    }

}
