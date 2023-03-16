package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.TreeSet;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double totalPrice = 0;
        if (coupons == null && products != null) {
            totalPrice = calculateTotalPriceWithoutCoupons(products);
        } else if (coupons != null && products != null) {
            if (coupons.size() == 1 && coupons.get(0).getCategory() != null) {
                totalPrice = calculateTotalPriceWithOneCouponWithCategory(coupons, products);
            } else if (coupons.size() == 1 && coupons.get(0).getCategory() == null) {
                totalPrice = calculateTotalPriceWithOneCouponWithoutCategory(coupons, products);
            } else if (coupons.size() > 1) {
                totalPrice = calculateTotalPriceWithTwoCouponsWithCategory(coupons, products);
            }
        } else {
            totalPrice = 0;
        }
        return totalPrice;
    }

    private double calculateTotalPriceWithTwoCouponsWithCategory(List<Coupon> coupons, List<Product> products) {
        TreeSet<Double> couponDiscount = new TreeSet<>();
        double totalPrice = 0;
        for (Coupon coupon : coupons) {
            for (Product product : products) {
                if (product.getCategory() == coupon.getCategory()) {
                    totalPrice += product.getPrice() - (product.getPrice() * coupon.getDiscountValueInPercents() * 0.01);
                } else if (product.getCategory() != coupon.getCategory() && coupon.getCategory() != null) {
                    totalPrice += product.getPrice();
                } else if (coupon.getCategory() == null) {
                    totalPrice += product.getPrice() - (product.getPrice() * coupon.getDiscountValueInPercents() * 0.01);
                }
            }
            couponDiscount.add(totalPrice);
            totalPrice = 0;
        }
        return roundNumber(couponDiscount.first());
    }

    private static double calculateTotalPriceWithOneCouponWithoutCategory(List<Coupon> coupons, List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice() - (product.getPrice() * coupons.get(0).getDiscountValueInPercents() * 0.01);
        }
        return roundNumber(totalPrice);
    }

    private static double calculateTotalPriceWithOneCouponWithCategory(List<Coupon> coupons, List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            if (product.getCategory() == coupons.get(0).getCategory()) {
                totalPrice += product.getPrice() - (product.getPrice() * coupons.get(0).getDiscountValueInPercents() * 0.01);
            } else if (product.getCategory() != coupons.get(0).getCategory()) {
                totalPrice += product.getPrice();
            }
        }
        return roundNumber(totalPrice);
    }

    private static double roundNumber(double totalPrice) {
        BigDecimal totalPriceInBigDecimal = BigDecimal.valueOf(totalPrice);
        totalPriceInBigDecimal = totalPriceInBigDecimal.setScale(2, RoundingMode.HALF_DOWN);
        return totalPriceInBigDecimal.doubleValue();
    }

    private double calculateTotalPriceWithoutCoupons(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}