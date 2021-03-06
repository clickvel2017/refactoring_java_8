package iteration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LoopingDemo {
    private static Logger logger = Logger.getLogger(LoopingDemo.class.getName());
    public static final Predicate<String> EVEN_LENGTHS = s -> s.length() % 2 == 0;
    public static final Predicate<String> ODD_LENGTHS = EVEN_LENGTHS.negate();


    public List<String> evenLengths(List<String> strings) {
        return strings.stream()
                .filter(s -> s.length() % 2 == 0)
                .collect(Collectors.toList());
    }

    public List<String> oddLengths(List<String> strings) {
        return filteredStrings(strings, s -> s.length() % 2 != 0);
    }

    public List<String> filteredStrings(List<String> strings, Predicate<String> predicate) {
        return strings.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        List<String> strings = Arrays.asList(
                "this", "is", "a", "list", "of", "strings");
        // logger.info(() -> "Here is my message: " + strings);

        // Before:
        for (String s : strings) {
            System.out.println(s);
        }

        // Anonymous inner class impl
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // After:
        strings.forEach(x -> System.out.println("The value of x is " + x));
        strings.forEach(System.out::println);

        strings.stream()                    // Stream<String>
                // .map(String::length)     // Stream<Integer>
                .mapToInt(String::length)   // IntStream
                .forEach(System.out::println);

        strings = Arrays.asList(
                "this", "is", null, "a", "list", null, "of", "strings");
        System.out.println("Lengths: ");
        List<Integer> lengths = strings.stream()
                .filter(Objects::nonNull)
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(lengths);


        Predicate<String> evens = s -> s.length() % 2 == 0;
        Predicate<String> nonNull = Objects::nonNull;

        System.out.println("Joining even length strings:");
        String total = strings.stream()
                // .filter(s -> s != null && s.length() % 2 == 0)
                .filter(nonNull.and(evens))
//                .map(s -> {
//                    System.out.println(s);
//                    return s;
//                })
//                .peek(s -> System.out.println("The value of s is " + s))
                .map(String::toUpperCase)
                .collect(Collectors.joining(","));
        System.out.println(total);
    }
}
