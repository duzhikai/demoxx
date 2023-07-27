package stream;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Demo {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4, 7, 9, 11, 12);
        list.stream()
                .peek(x -> System.out.println("stream: " + x))
                .map(x -> x + 2)
                .peek(x -> System.out.println("map: " + x))
                .filter(x -> x % 2 != 0)
                .peek(x -> System.out.println("filter: " + x))
                .limit(2)
                .peek(x -> System.out.println("limit: " + x))
                .collect(toList());
    }
}
