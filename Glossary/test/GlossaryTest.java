import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class GlossaryTest {

    /*
     * Test of concatenation
     */

    //routine
    @Test
    public void testConcatenation_input1() {
        SimpleReader input = new SimpleReader1L(
                "http://web.cse.ohio-state.edu/software/2221/web-sw1/assignments/projects/glossary/data/terms.txt");
        Queue<String> concat = Glossary.concatenation(input);
        Queue<String> expect = concat.newInstance();
        expect.enqueue(
                "meaning,something that one wishes to convey, especially by language");
        expect.enqueue("term,a word whose definition is in a glossary");
        expect.enqueue(
                "word,a string of characters in a language, which has at least one character");
        expect.enqueue(
                "definition,a sequence of words that gives meaning to a term");
        expect.enqueue(
                "glossary,a list of difficult or specialized terms, with their definitions,usually near the end of a book");
        expect.enqueue(
                "language,a set of strings of characters, each of which has meaning");
        expect.enqueue("book,a printed or written literary work");

        assertEquals(expect, concat);
    }

    /*
     * Test of terms
     */

    //routine
    @Test
    public void testTerms_concatenate1() {
        Queue<String> concat = new Queue1L<>();
        concat.enqueue("abc,def ghi");
        concat.enqueue("hij,klm nop");
        concat.enqueue("opq,rst uvw");
        Set<String> terms = Glossary.terms(concat);
        Set<String> expect = terms.newInstance();
        expect.add("abc");
        expect.add("hij");
        expect.add("opq");
        assertEquals(expect, terms);
    }

    //routine
    @Test
    public void testTerms_concatenate2() {
        Queue<String> concat = new Queue1L<>();
        concat.enqueue("abc,aaa aaaa aaaaa");
        concat.enqueue("def,aaa aaaa aaaaa");
        Set<String> terms = Glossary.terms(concat);
        Set<String> expect = terms.newInstance();
        expect.add("abc");
        expect.add("def");
        assertEquals(expect, terms);
    }

    //challenging
    @Test
    public void testTerms_concatenate3() {
        Queue<String> concat = new Queue1L<>();
        concat.enqueue("abc,,xxx xxxx");
        concat.enqueue("aba,,,xxx xxxx");
        Set<String> terms = Glossary.terms(concat);
        Set<String> expect = terms.newInstance();
        expect.add("abc");
        expect.add("aba");
        assertEquals(expect, terms);
    }

    /*
     * Test of generateElements
     */

    //routine
    @Test
    public void testGenerateElements_str_strSet1() {
        String str = ",./;";
        Set<Character> strSet = new Set1L<>();
        Glossary.generateElements(str, strSet);
        Set<Character> expect = strSet.newInstance();
        expect.add(',');
        expect.add('.');
        expect.add('/');
        expect.add(';');
        assertEquals(strSet, expect);
    }

    //routine
    @Test
    public void testGenerateElements_str_strSet2() {
        String str = "abcd";
        Set<Character> strSet = new Set1L<>();
        Glossary.generateElements(str, strSet);
        Set<Character> expect = strSet.newInstance();
        expect.add('a');
        expect.add('b');
        expect.add('c');
        expect.add('d');
        assertEquals(strSet, expect);
    }

    //challenging
    @Test
    public void testGenerateElements_str_strSet3() {
        String str = "12341234";
        Set<Character> strSet = new Set1L<>();
        Glossary.generateElements(str, strSet);
        Set<Character> expect = strSet.newInstance();
        expect.add('1');
        expect.add('2');
        expect.add('3');
        expect.add('4');
        assertEquals(strSet, expect);
    }

    /*
     * Test of nextWord
     */

    //routine
    @Test
    public void testNextWord_text_position1() {
        String text = "abc,efg";
        int position = 1;
        String next = Glossary.nextWord(text, position);
        String expect = "bc";
        assertEquals(expect, next);
    }

    //routine
    @Test
    public void testNextWord_text_position2() {
        String text = "abc,./;efg";
        int position = 3;
        String next = Glossary.nextWord(text, position);
        String expect = ",./;";
        assertEquals(expect, next);
    }

    //routine
    @Test
    public void testNextWord_text_position3() {
        String text = "abcdefghijklmn,.<>?:\"{}|[]-+";
        int position = 14;
        String next = Glossary.nextWord(text, position);
        String expect = ",.<>?:\"{}|[]-+";
        assertEquals(expect, next);
    }

    /*
     * Test of replaceWords
     */

    //routine
    @Test
    public void testReplaceWords_word_terms1() {
        Set<String> terms = new Set1L<>();
        terms.add("to");
        terms.add("be");
        String word = "to";
        String replace = Glossary.replaceWords(word, terms);
        String expect = "<a href=\"to.html\">to</a>";
        assertEquals(replace, expect);
        assertEquals(word, "to");
        assertTrue(terms.contains("to"));
        assertTrue(terms.contains("be"));
        assertEquals(terms.size(), 2);
    }

    //routine
    @Test
    public void testReplaceWords_word_terms2() {
        Set<String> terms = new Set1L<>();
        terms.add("apple");
        terms.add("watermelon");
        terms.add("pineapple");
        String word = "jackfruit";
        String replace = Glossary.replaceWords(word, terms);
        String expect = "jackfruit";
        assertEquals(replace, expect);
        assertTrue(terms.contains("apple"));
        assertTrue(terms.contains("watermelon"));
        assertTrue(terms.contains("pineapple"));
        assertEquals(terms.size(), 3);
    }

}
