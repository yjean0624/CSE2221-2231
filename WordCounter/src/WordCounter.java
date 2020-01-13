import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Build a program to count word occurrences.
 *
 * @author Xingyue Zhao
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * @param input
     *            source of terms and definitions, one per line
     * @return separateWords set of strings from {@code input}
     * @requires input.is_open
     * @ensures <pre>
     * input.is_open and input.content = <>
     * concatenation = terms * definitions
     * </pre>
     */
    private static Map<String, Integer> separateWords(SimpleReader input) {
        assert input != null : "Violation of: input is not null";
        Map<String, Integer> terms = new Map1L<>();
        final String separatorStr = " \t, \n\r,.<>/?;:\"'{}[]_-+=~`!@#$%^&*()|";
        Set<Character> separator = new Set1L<>();
        //separators elements
        generateElements(separatorStr, separator);
        while (!input.atEOS()) {
            StringBuilder line = new StringBuilder(input.nextLine());
            int position = 0;
            while (position < line.length()) {
                //separate each word and separator
                String word = nextWord(line.toString(), position);
                //check if it is a word
                if (Character.isLetter(word.charAt(0))) {
                    int check = 1;
                    //key have existed in the map
                    if (terms.hasKey(word)) {
                        check += terms.value(word);
                        terms.replaceValue(word, check);
                        //key not existed in the map
                    } else {
                        check += 0;
                        terms.add(word, check);
                    }
                }

                position += word.length();

            }

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
        //Separators
        final String separatorStr = " \t, \n\r,.<>/?;:\"'{}[]_-+=~`!@#$%^&*()|";
        Set<Character> separator = new Set1L<>();
        generateElements(separatorStr, separator);
        String part = text.substring(position);
        StringBuilder out = new StringBuilder();
        //text from position is a separator
        if (separator.contains(text.charAt(position))) {
            int i = 0;
            boolean check = true;
            //check until reach a char not in set of separators
            while (i < part.length() && check) {
                if (separator.contains(part.charAt(i))) {
                    out.append(part.charAt(i));
                } else {
                    check = false;
                }
                i++;
            }
            //text from position is a syllabus
        } else {
            int j = 0;
            boolean check = true;
            //check until reach a char in the set of separators
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
     * Take keys in Map and added into Queue.
     *
     * @param terms
     *            the intended Map
     * @return key Queue of keys
     * @requires terms.is_0pen
     * @ensure key.content = <>
     */
    private static Queue<String> key(Map<String, Integer> terms) {
        Queue<String> termsKey = new Queue1L<>();
        Map<String, Integer> termsTemp = new Map1L<>();
        while (terms.size() > 0) {
            Map.Pair<String, Integer> temp = terms.removeAny();
            //trace selected map key
            String tempStr = temp.key();
            //trace selected map value
            int tempInt = temp.value();
            termsTemp.add(tempStr, tempInt);
            termsKey.enqueue(tempStr);
        }
        //restore
        terms.transferFrom(termsTemp);
        return termsKey;
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        //input file name
        out.print("Please input the name of the input file: ");
        String name = in.nextLine();
        SimpleReader inName = new SimpleReader1L(name);
        //check whether the input file is an empty file
        if (!inName.atEOS()) {
            Map<String, Integer> terms = separateWords(inName);
            Comparator<String> order = new StringLT();
            Queue<String> termsKey = key(terms);
            termsKey.sort(order);
            //output file
            out.print("Please input the name of the output file: ");
            String file = in.nextLine();
            //index page
            SimpleWriter index = new SimpleWriter1L(file);
            index.println("<html>");
            index.println("<head>");
            index.println("<title>Words Counted in " + name + "</title>");
            index.println("</head>");
            index.println("<body>");
            index.println("<h2>Words Counted in " + name + "</h2>");
            index.println("<hr />");
            index.println("<table border= \"1\">");
            index.println("<tr>");
            index.println("<th>Words</th>");
            index.println("<th>Counts</th>");
            index.println("</tr>");
            //loop until no words in the queue
            while (termsKey.length() > 0) {
                String termStr = termsKey.dequeue();
                int termInt = terms.value(termStr);
                index.println("<tr>");
                index.println("<td>" + termStr + "</td>");
                index.println("<td>" + termInt + "</td>");
                index.println("</tr>");
            }
            index.println("</table>");
            index.println("</body>");
            index.println("</html>");
            index.close();
        }
        out.close();
        in.close();
    }

}
