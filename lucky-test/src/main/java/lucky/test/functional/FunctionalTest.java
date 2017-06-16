package lucky.test.functional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author:chaoqiang.zhou
 * @Description:java1.8函数式接口练习
 * @Date:Create in 17:02 2017/6/14
 */
public class FunctionalTest {

    public void test1() {
        final List<BigDecimal> prices = Arrays.asList(
                new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
                new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"),
                new BigDecimal("45"), new BigDecimal("12"));

        final BigDecimal totalOfDiscountedPrices =
                prices.stream()
                        .filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
                        .map(price -> price.multiply(BigDecimal.valueOf(0.9)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);


    }


    public static void main(String[] args) {
        FunctionalTest test = new FunctionalTest();
        test.test3();
    }

    // 支持lamda表达式的接口只允许定义一个抽象方法(@FunctionalInterface注解的接口,只允许定义一个抽象方法)
    public void test2() {

        Function<Integer, String> function = (x) -> "test result" + x;
        Function<String, String> function1 = x -> {
            return "after function";
        };
        System.out.println(function.apply(6));
        System.out.println(function.andThen(function1).apply(6));
    }


    public void test3() {
        Supplier<String> supplier = () -> "test supplier";
        System.out.println(supplier.get());
    }


    public void test4() {
        Consumer<String> consumer = (x) -> {
            System.out.println(x);
        };
        consumer.accept("dd");
    }


    public void testPredicate() {
        Predicate<String> predicate = (x) -> x.length() > 0;
        System.out.println(predicate.test("String"));
    }


    public void test8() {
        CustomerLambda<String> customerLambda = (x) -> {
            x.accept("6");
            return "6";
        };
        Consumer<String> consumer = (x) -> {
            System.out.println("test" + x);
        };
        customerLambda.customFunction(consumer);
    }

}
