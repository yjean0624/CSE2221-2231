import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Xingyue Zhao & Runting Shao
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires [<"INSTRUCTION"> is a proper prefix of tokens]
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";
        //check INSTRUCTION
        Reporter.assertElseFatalError(tokens.dequeue().equals("INSTRUCTION"),
                "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens");
        String name = tokens.dequeue(); //dequeue identifier
        //check identifier not primitive instructions
        Reporter.assertElseFatalError(
                !name.equals("move") && !name.equals("turnleft")
                        && !name.equals("turnright") && !name.equals("infect")
                        && !name.equals("skip"),
                "Name of instruction cannot be the name of primitive instructions");
        //check IS
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Syntax errors: lack \"IS\"");
        body.parseBlock(tokens);
        //check END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Syntax errors: lack \"END\"");
        //check end of identifier
        Reporter.assertElseFatalError(tokens.dequeue().equals(name),
                "Identifiers must be the same at the beginning and end of instruction");

        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";
        Set<String> instrNames = new Set1L<>();
        //check PROGRAM
        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Violation of: <\"PROGRAM\"> is proper prefix of tokens");
        String name = tokens.dequeue(); //dequeue identifier
        //check program identifier is valid
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(name),
                "Violation of: Program Identifier is valid");
        //check IS
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Syntax errors: lack \"IS\"");
        Map<String, Statement> c = this.newContext();
        //check INSTRUCTION
        while (tokens.front().equals("INSTRUCTION")) {
            Statement body = this.newBody();
            String instrName = parseInstruction(tokens, body);
            Reporter.assertElseFatalError(!instrNames.contains(instrName),
                    "Name of instruction must be unique"); //check duplicate identifier
            instrNames.add(instrName);
            c.add(instrName, body);
        }
        //check BEGIN
        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Syntax error: lack \"BEGIN\"");
        Statement b = this.newBody();
        b.parseBlock(tokens);
        //check END
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Syntax error: lack \"END\"");
        //check end of identifier of program
        Reporter.assertElseFatalError(tokens.dequeue().equals(name),
                "Identifiers must be the same at the beginning and end of program");
        //check Tokenizer.END_OF_INPUT is suffix
        Reporter.assertElseFatalError(
                tokens.dequeue().equals(Tokenizer.END_OF_INPUT),
                "\"Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens\"");
        this.replaceContext(c);
        this.replaceBody(b);
        this.replaceName(name);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
