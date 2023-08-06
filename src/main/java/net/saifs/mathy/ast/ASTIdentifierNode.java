package net.saifs.mathy.ast;

public class ASTIdentifierNode extends AbstractSyntaxTreeNode {
    private final String identifier;

    public ASTIdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "ASTIdentifierNode{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
