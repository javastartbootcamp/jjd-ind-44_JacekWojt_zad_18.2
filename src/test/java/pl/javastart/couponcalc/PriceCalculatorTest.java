package pl.javastart.couponcalc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    @Test
    public void shouldReturnZeroForNoProducts() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();

        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForProductsAndOneCouponWithoutCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Lampa do salonu", 249.90, Category.HOME));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(null, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(204.71);
    }

    @Test
    public void shouldReturnPriceForProductsAndTwoCouponsWithCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Banany", 12.50, Category.FOOD));
        products.add(new Product("Pomarańcze", 8.75, Category.FOOD));
        products.add(new Product("Lampa do salonu", 249.90, Category.HOME));
        products.add(new Product("Dywan", 550.90, Category.HOME));
        products.add(new Product("Wiedźmin - gra na PC", 35.80, Category.ENTERTAINMENT));
        products.add(new Product("Nowe opony do samochodu", 350.00, Category.CAR));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 25));
        coupons.add(new Coupon(Category.HOME, 15));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(1093.72);
    }

    @Test
    public void shouldReturnPriceForProductsAndThreeCouponsOneWithoutCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Banany", 12.50, Category.FOOD));
        products.add(new Product("Pomarańcze", 8.75, Category.FOOD));
        products.add(new Product("Lampa do salonu", 249.90, Category.HOME));
        products.add(new Product("Dywan", 550.90, Category.HOME));
        products.add(new Product("Wiedźmin - gra na PC", 35.80, Category.ENTERTAINMENT));
        products.add(new Product("Remont samochodu", 15000.00, Category.CAR));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 45));
        coupons.add(new Coupon(Category.HOME, 15));
        coupons.add(new Coupon(null, 5));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(15070.65);
    }
}