package net.saifs.mathy.evaluator;

import net.saifs.mathy.ast.*;

import java.util.HashMap;
import java.util.Map;

public class Evaluator {
    private final Map<String, Object> fields;

    public Evaluator(Map<String, Object> fields) {
        this.fields = fields;
    }

    public <T> T lookup(String identifier, Map<String, Object> fields) {
        if (fields.containsKey(identifier)) {
            return (T) fields.get(identifier);
        }

        if (this.fields.containsKey(identifier)) {
            return (T) this.fields.get(identifier);
        }

        throw new RuntimeException("unexpected identifier: " + identifier);
    }

    public double evaluate(AbstractSyntaxTreeNode node, Map<String, Object> fields) {
        if (node instanceof ASTBinaryNode binary) {
            double left = this.evaluate(binary.getLeft(), fields);
            double right = this.evaluate(binary.getRight(), fields);
            return binary.getOperation().apply(left, right);
        }

        if (node instanceof ASTCallNode call) {
            MathFunction function = this.lookup(call.getFunction(), fields);
            Double[] arguments = new Double[call.getArgs().size()];
            for (int i = 0; i < call.getArgs().size(); i++) {
                arguments[i] = this.evaluate(call.getArgs().get(i), fields);
            }
            return function.apply(arguments);
        }

        if (node instanceof ASTIdentifierNode identifier) {
            return this.lookup(identifier.getIdentifier(), fields);
        }

        if (node instanceof ASTNumberNode number) {
            return number.getValue();
        }

        throw new RuntimeException("invalid node " + node + " with type " + node.getClass().getName());
    }
}
