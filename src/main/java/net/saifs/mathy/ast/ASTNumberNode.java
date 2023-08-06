package net.saifs.mathy.ast;

public class ASTNumberNode extends AbstractSyntaxTreeNode {
    private final double value;

    public ASTNumberNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ASTNumberNode{" +
                "value=" + value +
                '}';
    }
}
