package net.saifs.mathy.ast;

import java.util.List;

public class ASTCallNode extends AbstractSyntaxTreeNode {
    private final String function;
    private final List<AbstractSyntaxTreeNode> args;

    public ASTCallNode(String function, List<AbstractSyntaxTreeNode> args) {
        this.function = function;
        this.args = args;
    }

    public String getFunction() {
        return function;
    }

    public List<AbstractSyntaxTreeNode> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "ASTCallNode{" +
                "function='" + function + '\'' +
                ", args=" + args +
                '}';
    }
}
