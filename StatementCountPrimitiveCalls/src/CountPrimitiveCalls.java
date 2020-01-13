import components.map.Map;
import components.program.Program;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */
                for (int n = 0; n < s.lengthOfBlock(); n++) {
                    Statement block = s.removeFromBlock(n);
                    count = count + countOfPrimitiveCalls(block);
                    s.addToBlock(n, block);
                }
                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */
                Statement st = s.newInstance();
                Condition cd = s.disassembleIf(st);
                count = countOfPrimitiveCalls(st);
                s.assembleIf(cd, st);
                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */
                Statement st = s.newInstance();
                Statement sd = s.newInstance();
                Condition cd = s.disassembleIfElse(st, sd);
                count = countOfPrimitiveCalls(st) + countOfPrimitiveCalls(sd);
                s.assembleIfElse(cd, st, sd);
                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */
                Statement st = s.newInstance();
                Statement.Condition cd = s.disassembleWhile(st);
                count = countOfPrimitiveCalls(st);
                s.assembleWhile(cd, st);
                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */
                String str = s.disassembleCall();
                if (str.equals("move") || str.equals("turnleft")
                        || str.equals("turnright") || str.equals("infect")
                        || str.equals("skip")) {
                    count++;
                }
                s.assembleCall(str);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
        return count;
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {
                for (int n = 0; n < s.lengthOfBlock(); n++) {
                    Statement st = s.removeFromBlock(n);
                    simplifyIfElse(st);
                    s.addToBlock(n, st);
                }
                break;
            }
            case IF: {
                Statement st = s.newInstance();
                Statement.Condition cd = s.disassembleIf(st);
                simplifyIfElse(st);
                s.assembleIf(cd, st);
                break;
            }
            case IF_ELSE: {
                Statement st = s.newInstance();
                Statement sd = s.newInstance();
                Statement.Condition cd = s.disassembleIfElse(st, sd);
                if (cd == Condition.NEXT_IS_NOT_EMPTY) {
                    cd = Condition.NEXT_IS_EMPTY;
                } else if (cd == Condition.NEXT_IS_NOT_ENEMY) {
                    cd = Condition.NEXT_IS_ENEMY;
                } else if (cd == Condition.NEXT_IS_NOT_FRIEND) {
                    cd = Condition.NEXT_IS_FRIEND;
                } else if (cd == Condition.NEXT_IS_NOT_WALL) {
                    cd = Condition.NEXT_IS_WALL;
                }
                simplifyIfElse(st);
                simplifyIfElse(sd);
                s.assembleIfElse(cd, sd, st);
                break;
            }
            case WHILE: {
                Statement st = s.newInstance();
                Statement.Condition cd = s.disassembleWhile(st);
                simplifyIfElse(st);
                s.assembleWhile(cd, st);
                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
    }

    public static void simplifyIfElse(Program p) {
        Map<String, Statement> c = p.newContext();
        Map<String, Statement> ctxt = p.replaceContext(c);
        while (ctxt.size() > 0) {
            Map.Pair<String, Statement> instr = ctxt.removeAny();
            String name = instr.key();
            Statement body = instr.value();
            // simplify IF_ELSE for body of instr (a Statement)
            simplifyIfElse(body);
            c.add(name, body);
        }
        p.replaceContext(c);
        Statement b = p.newBody();
        Statement pBody = p.replaceBody(b);
        // simplify IF_ELSE for pBody (a Statement)
        simplifyIfElse(pBody);
        p.replaceBody(pBody);
    }

}
