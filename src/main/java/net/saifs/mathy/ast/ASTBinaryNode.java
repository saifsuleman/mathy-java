package net.saifs.mathy.ast;

import net.saifs.mathy.evaluator.EvaluatorOperation;

public class ASTBinaryNode extends AbstractSyntaxTreeNode {
    private final AbstractSyntaxTreeNode left;
    private final AbstractSyntaxTreeNode right;
    private final EvaluatorOperation operation;

    public ASTBinaryNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right, EvaluatorOperation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    public AbstractSyntaxTreeNode getLeft() {
        return left;
    }

    public AbstractSyntaxTreeNode getRight() {
        return right;
    }

    public EvaluatorOperation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "ASTBinaryNode{" +
                "left=" + left +
                ", right=" + right +
                ", operation=" + operation +
                '}';
    }
}
