import components.map.Map;
import components.program.Program;
import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class HelloWorld {

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {
                for (int n = 0; n < s.lengthOfBlock(); n++) {
                    Statement temp = s.removeFromBlock(n);
                    renameInstruction(temp, oldName, newName);
                    s.addToBlock(n, temp);
                }
                break;
            }
            case IF: {
                Statement st = s.newInstance();
                Condition cd = s.disassembleIf(st);
                renameInstruction(st, oldName, newName);
                s.assembleIf(cd, st);
                break;
            }
            case IF_ELSE: {
                Statement st = s.newInstance();
                Statement sd = s.newInstance();
                Condition cd = s.disassembleIfElse(st, sd);
                renameInstruction(st, oldName, newName);
                renameInstruction(sd, oldName, newName);
                s.assembleIfElse(cd, st, sd);
                break;
            }
            case WHILE: {
                Statement st = s.newInstance();
                Condition cd = s.disassembleWhile(st);
                renameInstruction(st, oldName, newName);
                s.assembleWhile(cd, st);
                break;
            }
            case CALL: {
                String temp = s.disassembleCall();
                if (temp.equals(oldName)) {
                    temp = newName;
                }
                s.assembleCall(temp);
                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
    }

    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     *
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
        Map<String, Statement> temp = p.newContext();
        Map<String, Statement> newT = p.replaceContext(temp);
        while (newT.size() > 0) {
            Map.Pair<String, Statement> any = newT.removeAny();
            String key = any.key();
            Statement value = any.value();
            if (key.equals(oldName)) {
                key = newName;
            }
            renameInstruction(value, oldName, newName);
            temp.add(key, value);
        }
        p.replaceContext(temp);
        Statement tempS = p.newBody();
        Statement old = p.replaceBody(tempS);
        renameInstruction(old, oldName, newName);
        p.replaceBody(old);
    }

}
