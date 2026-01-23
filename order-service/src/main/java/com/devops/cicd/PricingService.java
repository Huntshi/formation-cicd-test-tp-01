package com.devops.cicd;

public final class PricingService {

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        double amountWithVat = amountExclVat;
        amountWithVat += amountExclVat * 0.20;

        return amountWithVat;
    }

    public double applyVipDiscount(double amount, boolean vip) {
        double amountWithVip;

        if (vip == true){
            amountWithVip = amount - (amount * 0.1);
            return amountWithVip;
        } else {
            return amount;
        }
    }

    public double shippingCost(double amount) {
        double shippingCost = 0;
        double shippingAmount = 4.99;

        if (amount < 50){
            shippingCost += shippingAmount;
        }

        return shippingCost;
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double amountTotal = 0;

        amountTotal = applyVat(amountExclVat);
        amountTotal = applyVipDiscount(amountTotal, vip);
        amountTotal += shippingCost(amountExclVat);

        return amountTotal;

    }
}