import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class NicknameService {
    private static final AtomicInteger beautifulWordsCounter3 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsCounter4 = new AtomicInteger(0);
    private static final AtomicInteger beautifulWordsCounter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeChecker = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    switch (text.length()) {
                        case 3 -> beautifulWordsCounter3.incrementAndGet();
                        case 4 -> beautifulWordsCounter4.incrementAndGet();
                        case 5 -> beautifulWordsCounter5.incrementAndGet();
                    }
                }
            }
        });

        Thread sameLetterChecker = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    switch (text.length()) {
                        case 3 -> beautifulWordsCounter3.incrementAndGet();
                        case 4 -> beautifulWordsCounter4.incrementAndGet();
                        case 5 -> beautifulWordsCounter5.incrementAndGet();
                    }
                }
            }
        });

        Thread increasingOrderChecker = new Thread(() -> {
            for (String text : texts) {
                if (isIncreasingOrder(text)) {
                    switch (text.length()) {
                        case 3 -> beautifulWordsCounter3.incrementAndGet();
                        case 4 -> beautifulWordsCounter4.incrementAndGet();
                        case 5 -> beautifulWordsCounter5.incrementAndGet();
                    }
                }
            }
        });

        palindromeChecker.start();
        sameLetterChecker.start();
        increasingOrderChecker.start();

        palindromeChecker.join();
        sameLetterChecker.join();
        increasingOrderChecker.join();

        System.out.println("Красивых слов с длиной 3: " + beautifulWordsCounter3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsCounter4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsCounter5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameLetter(String text) {
        return text.chars().allMatch(c -> c == text.charAt(0));
    }

    public static boolean isIncreasingOrder(String text) {
        return IntStream.range(0, text.length() - 1)
                .allMatch(i -> text.charAt(i) <= text.charAt(i + 1));
    }
}
