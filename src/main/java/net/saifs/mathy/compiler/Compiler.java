package net.saifs.mathy.compiler;

import net.saifs.mathy.ast.AbstractSyntaxTreeNode;
import net.saifs.mathy.evaluator.Evaluator;
import net.saifs.mathy.lexer.Lexer;
import net.saifs.mathy.parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Compiler {
    private final Parser parser;
    private final Evaluator evaluator;
    private final Map<String, Object> bindings = new HashMap<>();

    public Compiler() {
        Lexer lexer = new Lexer();
        this.parser = new Parser(lexer);
        this.evaluator = new Evaluator(this.bindings);
    }

    public void bind(String key, Object value) {
        this.bindings.put(key, value);
    }

    public Function<Map<String, Object>, Double> compile(String contents) {
        AbstractSyntaxTreeNode tree = this.parser.read(contents);
        return (env) -> this.evaluator.evaluate(tree, env);
    }
}
