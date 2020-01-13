import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Build a program to generate tag clouds using java.
 *
 * @author Xingyue Zhao & Runting Shao
 */
public final class TagCloudJCF {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Definition of separators.
     */
    private static final String SEPARATORS = " \t, \n\r,.<>/?;:\"'{}[]_-+=~`!@#$%^&*()|";

    /**
     * Definition of fMax.
     */
    private final static int FMAX = 48;

    /**
     * Definition of fMin.
     */
    private static final int FMIN = 11;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudJCF() {
    }

    /**
     * Counting occurrence of words in Map using {@code input}.
     *
     * @param input
     *            source of terms and definitions, one per line
     * @return separateWords set of strings from {@code input}
     * @throws IOException
     *             output an error message when something goes wrong
     * @requires input.is_open
     * @ensures <pre>
     * input.is_open and input.content = <>
     * concatenation = terms * definitions
     * </pre>
     */
    private static Map<String, Integer> separateWords(BufferedReader input)
            throws IOException {
        assert input != null : "Violation of: input is not null";
        Map<String, Integer> terms = new HashMap<>();
        Set<Character> separator = new HashSet<>();
        generateElements(SEPARATORS, separator);
        String line = input.readLine();
        while (line != null) {
            int position = 0;
            while (position < line.length()) {
                String word = nextWord(line.toString(), position).toLowerCase();
                if (Character.isLetter(word.charAt(0))) {
                    if (terms.containsKey(word)) {
                        int value = terms.remove(word);
                        value++;
                        terms.put(word, value);
                    } else {
                        terms.put(word, 1);
                    }
                }
                position += word.length();
            }
            line = input.readLine();
        }
        return terms;
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!strSet.contains(a)) {
                strSet.add(a);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWord(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        Set<Character> separator = new HashSet<>();
        generateElements(SEPARATORS, separator);
        String part = text.substring(position);
        StringBuilder out = new StringBuilder();
        if (separator.contains(text.charAt(position))) {
            int i = 0;
            boolean check = true;
            while (i < part.length() && check) {
                if (separator.contains(part.charAt(i))) {
                    out.append(part.charAt(i));
                } else {
                    check = false;
                }
                i++;
            }
        } else {
            int j = 0;
            boolean check = true;
            while (j < part.length() && check) {
                if (!separator.contains(part.charAt(j))) {
                    out.append(part.charAt(j));
                } else {
                    check = false;
                }
                j++;
            }
        }
        return out.toString();
    }

    /**
     * Compare {@code int}s in decreasing order.
     */
    private static class IntegerCompare
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringCompare
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    }

    /**
     * Font size of the word.
     *
     * @param fMin
     *            minimal domain
     * @param min
     *            minimal range
     * @param k
     *            slope
     * @param pair
     *            one pair of word
     * @ensures 11 <= f <= 48
     * @return f
     */
    private static int fontSize(int fMin, int min, double k,
            Map.Entry<String, Integer> pair) {
        int pairValue = pair.getValue();
        int f = (int) ((pairValue - min) / k) + fMin;
        return f;
    }

    /**
     * Output heading of the file.
     *
     * @param out
     *            output stream
     * @param number
     *            user input number
     * @param nameIn
     *            user input name
     * @requires out.is_open
     * @ensures <pre>
     * out.is_open and output the file into {@code out}
     * </pre>
     */
    public static void heading(PrintWriter out, int number, String nameIn) {
        assert out != null : "Violation of: out is not null";
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<title>Top " + number + " words in " + nameIn + "</title>");
        out.println("<link href=\"http://cse.osu.edu/software/2231/web-sw2/"
                + "assignments/proje"
                + "cts/tag-cloud-generator/data/tagcloud.css\" rel=\"sty"
                + "lesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + number + " words in " + nameIn + "</h2>");
        out.println("<hr>");
        out.println("<div class =\"cdiv\">");
        out.println("<p class=\"cbox\">");

    }

    /**
     * output content of the file.
     *
     * @param sort
     *            sorted map
     * @param number
     *            input number
     * @param out
     *            output stream
     * @update sort
     */
    public static void content(Map<String, Integer> sort, int number,
            PrintWriter out) {
        assert out != null : "Violation of: out is not null";
        List<Map.Entry<String, Integer>> intSort = new ArrayList<>(
                sort.entrySet());
        Comparator<Map.Entry<String, Integer>> intOrder = new IntegerCompare();
        Collections.sort(intSort, intOrder);
        List<Map.Entry<String, Integer>> stringSort = new ArrayList<>(
                intSort.subList(0, number));
        Comparator<Map.Entry<String, Integer>> stringOrder = new StringCompare();
        Collections.sort(stringSort, stringOrder);
        //most frequency of the word
        Map.Entry<String, Integer> max = intSort.remove(0);
        while (intSort.size() > 1) {
            intSort.remove(0);
        }
        //least frequency of the word
        Map.Entry<String, Integer> min = intSort.remove(0);
        double k = (double) (max.getValue() - min.getValue()) / (FMAX - FMIN);
        int n = 0;
        while (n < number) {
            Map.Entry<String, Integer> temp = stringSort.remove(0);
            out.println("<span style=\"cursor:default\" class=\"f"
                    + fontSize(FMIN, min.getValue(), k, temp)
                    + "\" title=\"count: " + temp.getValue() + "\">"
                    + temp.getKey() + "</span>");
            n++;
        }
    }

    /**
     * Output ending of the file.
     *
     * @param out
     *            output stream
     * @requires out.is_open
     * @ensures <pre>
     * out.is_open and output the file into {@code out}
     * </pre>
     */
    public static void ending(PrintWriter out) {
        assert out != null : "Violation of: out is not null";
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.print("Please input the name of the input file: ");
        String nameIn = "";
        BufferedReader input = null;
        try {
            nameIn = in.readLine();
            input = new BufferedReader(new FileReader(nameIn));
        } catch (IOException e) {
            System.err.println("Violation of: File can be found.");
            return;
        }
        System.out.print("Please input the name of the output file: ");
        String nameOut = "";
        PrintWriter output = null;
        try {
            nameOut = in.readLine();
            final int five = 5;
            //check suffix
            if (nameOut != null && nameOut
                    .substring(nameOut.length() - five, nameOut.length())
                    .equals(".html")) {
                output = new PrintWriter(
                        new BufferedWriter(new FileWriter(nameOut)));
            } else {
                System.err.println("Violation: Output file format correct.");
                try {
                    input.close();
                } catch (IOException e1) {
                    System.err.println(
                            "Violation of: Input stream is unable to close.");
                }
                return;
            }
        } catch (IOException e) {
            System.err.println("Violation of: File can be written.");
            try {
                input.close();
            } catch (IOException e1) {
                System.err.println(
                        "Violation of: Input stream is unable to close.");
            }
            return;
        }
        System.out.print("Please enter the number of words to be included: ");
        int number = 0;
        try {
            number = Integer.parseInt(in.readLine());
        } catch (IOException e1) {
            System.err.println("Violation of: Number form is correct.");
            output.close();
            try {
                input.close();
            } catch (IOException e) {
                System.err.println(
                        "Violation of: Input stream is unable to close.");
            }
            return;
        }
        Map<String, Integer> word = null;
        try {
            word = separateWords(input);
        } catch (IOException e) {
            System.err.println("Violation of: Words can sort into map.");
            output.close();
            try {
                input.close();
            } catch (IOException e1) {
                System.err.println(
                        "Violation of: Input stream is unable to close.");
            }
            return;
        }
        if (word != null && word.size() != 0) {
            if (number > word.size()) {
                output.println("Violation of: Index in range.");
            } else {
                heading(output, number, nameIn);
                content(word, number, output);
                ending(output);
            }
        } else {
            output.println("Violation of: File is not empty or there"
                    + " is word available to sort.");
        }
        try {
            input.close();
        } catch (IOException e) {
            System.err.println("Violation of: Input stream can close.");
            return;
        }
        output.close();
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Violation of: In stream can close.");
            return;
        }

    }

}
