import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Build a easy-to-maintain glossary facility.
 *
 * @author Sarah Zhao
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Returns queue of strings concatenating terms and definitions from
     * {@code input}.
     *
     * @param input
     *            source of terms and definitions, one per line
     * @return set of strings read from {@code input}
     * @requires input.is_open
     * @ensures <pre>
     * input.is_open and input.content = <>
     * concatenation = terms * definitions
     * </pre>
     */
    public static Queue<String> concatenation(SimpleReader input) {
        assert input != null : "Violation of: input is not null";
        //new empty Queue
        Queue<String> concat = new Queue1L<>();
        //check whether reach the end
        while (!input.atEOS()) {
            //make sure term and definition
            StringBuilder term = new StringBuilder();
            StringBuilder definition = new StringBuilder();
            //check per line
            StringBuilder line = new StringBuilder(input.nextLine());
            //if line is term
            if (line.indexOf(" ") == -1 && line.length() > 0) {
                term = line;
                //check next line
                line = new StringBuilder(input.nextLine());
                //line is definition
                //appends it to the former line until reach an empty line
                while (line.length() > 0) {
                    definition.append(line);
                    definition.append(" ");
                    line = new StringBuilder(input.nextLine());
                }
            }
            //combine term and definition with a ","
            String add = term + "," + definition;
            //add one entry to queue
            concat.enqueue(add);
        }
        return concat;
    }

    /**
     * Separate terms and definitions from each entry of {@code concatenate}.
     *
     * @param concatenate
     *            queue of glossary
     * @return set of terms
     * @requires concatenate
     * @ensures |terms| = |concatenate|
     */
    public static Set<String> terms(Queue<String> concatenate) {
        assert concatenate != null : "Violation of: concatenate is not null";
        //new Set
        Set<String> term = new Set1L<>();
        int tries = concatenate.length();
        //check until reach the end
        while (tries > 0) {
            //select one entry
            String a = concatenate.dequeue();
            concatenate.enqueue(a);
            //divide term and definition
            //choose term
            String aSub = a.substring(0, a.indexOf(','));
            //add one entry to set
            term.add(aSub);
            tries--;
        }
        return term;
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
    public static void generateElements(String str, Set<Character> strSet) {
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
    public static String nextWord(String text, int position) {
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
     * Check whether {@code terms} contains {@code word} and return the string
     * be modified.
     *
     * @param word
     *            single word need to be checked
     * @param terms
     *            set of string containing terms
     * @return {@code word} be modified if found in {@code terms} and vice versa
     * @requires |terms| > 0, |word| > 0
     * @ensures |replace| >= |word|
     */
    public static String replaceWords(String word, Set<String> terms) {
        assert terms != null : "Violation of: terms is not null";
        assert word != null : "Violation of: word is not null";
        String replace = "";
        //replace any word in the terms set into a specific form
        if (terms.contains(word)) {
            replace = replace + "<a href=\"" + word + ".html\">" + word
                    + "</a>";
        } else {
            replace = word;
        }
        return replace;
    }

    /**
     * Process one word from the given string converting it into a corresponding
     * HTML output file.
     *
     * @param concat
     *            one entry from queue of words
     * @param branchOut
     *            output stream
     * @param terms
     *            set of terms
     * @updates {@code branchOut}
     * @requires out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [term be in red boldface italics before its definition]
     * </pre>
     *
     */
    public static void processIndividualPage(String concat,
            SimpleWriter branchOut, Set<String> terms) {
        assert branchOut != null : "Violation of: branchOut is not null";
        assert terms != null : "Violation of: terms is not null";
        assert concat != null : "Violation of: concat is not null";
        //separate term and definition in the given string
        String term = concat.substring(0, concat.indexOf(','));
        String definition = concat.substring(concat.indexOf(',') + 1);
        //output individual page
        branchOut.println("<html>");
        branchOut.println("<head>");
        branchOut.println("<title>" + term + "</title>");
        branchOut.println("</head>");
        branchOut.println("<body>");
        branchOut.println("<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>");
        int position = 0;
        StringBuilder replace = new StringBuilder();
        //check each word in the definition whether they need to be replaced
        while (position < definition.length()) {
            String token = nextWord(definition, position);
            replace.append(replaceWords(token, terms));
            position += token.length();
        }
        //replace word
        branchOut.println("<blockquote>" + replace + "</blockquote>");
        branchOut.println("<hr />");
        branchOut.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        branchOut.println("</body>");
        branchOut.println("</html>");

    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
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
        //check whether the input file is a empty file
        if (!inName.atEOS()) {
            //new Queue read from input file
            Queue<String> concatenate = concatenation(inName);
            Comparator<String> order = new StringLT();
            //sort Queue
            concatenate.sort(order);
            //a set of terms
            Set<String> terms = terms(concatenate);
            //output file
            out.print("Please input the name of the output file: ");
            String file = in.nextLine();
            //index page
            SimpleWriter index = new SimpleWriter1L(file + "/index.html");
            index.println("<html>");
            index.println("<head>");
            index.println("<title>Glossary</title>");
            index.println("</head>");
            index.println("<body>");
            index.println("<h2>Glossary</h2>");
            index.println("<hr />");
            index.println("<h3>Index</h3>");
            index.println("<ul>");
            //loop until no words in the queue
            int size = concatenate.length();
            while (size > 0) {
                String a = concatenate.dequeue();
                //separate terms from one entry
                String term = a.substring(0, a.indexOf(','));
                //individual output stream
                SimpleWriter output = new SimpleWriter1L(
                        file + "/" + term + ".html");
                index.println("<li><a href=\"" + term + ".html\">" + term
                        + "</a></li>");
                //individual page
                processIndividualPage(a, output, terms);
                size--;
            }
            index.println("</ul>");
            index.println("</body>");
            index.println("</html>");
            index.close();
        }
        out.close();
        in.close();
    }

}
