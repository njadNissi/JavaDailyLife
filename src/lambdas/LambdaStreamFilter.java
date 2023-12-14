package lambdas;

import java.util.List;

public class LambdaStreamFilter {


    public static void main(String[] args) {

        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.stream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * 2)
                .forEach(System.out::println);

    }
}
