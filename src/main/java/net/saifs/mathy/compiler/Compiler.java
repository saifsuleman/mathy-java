package net.saifs.mathy.compiler;

import net.saifs.mathy.ast.*;
import net.saifs.mathy.lexer.Lexer;
import net.saifs.mathy.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Compiler {
    private final Parser parser;
    private final Map<String, Object> bindings = new HashMap<>();

    public Compiler() {
        Lexer lexer = new Lexer();
        this.parser = new Parser(lexer);
    }

    public void bind(String key, Object value) {
        this.bindings.put(key, value);
    }

    public Function<Map<String, Object>, Double> compile(String contents) {
        AbstractSyntaxTreeNode tree = this.parser.read(contents);
        return assemble(tree);
    }

    public <T> T lookup(String identifier, Map<String, Object> fields) {
        if (fields.containsKey(identifier)) {
            return (T) fields.get(identifier);
        }

        if (this.bindings.containsKey(identifier)) {
            return (T) this.bindings.get(identifier);
        }

        throw new RuntimeException("unexpected identifier: " + identifier);
    }

    public AbstractSyntaxTreeNode parse(String contents) {
        return parser.read(contents);
    }

    private Function<Map<String, Object>, Double> assemble(AbstractSyntaxTreeNode node) {
        if (node instanceof ASTBinaryNode binary) {
            Function<Map<String, Object>, Double> left = assemble(binary.getLeft());
            Function<Map<String, Object>, Double> right = assemble(binary.getRight());
            return (fields) -> binary.getOperation().apply(left.apply(fields), right.apply(fields));
        }

        if (node instanceof ASTCallNode call) {
            List<Function<Map<String, Object>, Double>> functions = new ArrayList<>();
            for (int i = 0; i < call.getArgs().size(); i++) {
                functions.add(this.assemble(call.getArgs().get(i)));
            }
            return (fields) -> {
                MathFunction function = this.lookup(call.getFunction(), fields);
                Double[] arguments = new Double[call.getArgs().size()];
                for (int i = 0; i < call.getArgs().size(); i++) {
                    arguments[i] = functions.get(i).apply(fields);
                }
                return function.apply(arguments);
            };
        }

        if (node instanceof ASTIdentifierNode identifier) {
            return (fields) -> this.lookup(identifier.getIdentifier(), fields);
        }

        if (node instanceof ASTNumberNode number) {
            return (fields) -> number.getValue();
        }

        throw new RuntimeException("invalid node " + node + " with type " + node.getClass().getName());
    }
}
