package lambdas;

import java.util.List;
import java.util.function.Predicate;

public class LambdaSelector {


    public static void main(String[] args) {
        var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println(totalValues(numbers, e -> true));
        System.out.println(totalValues(numbers, e -> e % 2 == 0));
        System.out.println(totalValues(numbers, e -> e % 2 != 0));
    }

    private static int totalValues(List<Integer> values, Predicate<Integer> selector) {
        var result = 0;

        for(var e: values){
            if(selector.test(e)){
                result += e;
            }
        }

        return result;
    }
}
