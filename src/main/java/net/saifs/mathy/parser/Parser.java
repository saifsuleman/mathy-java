package net.saifs.mathy.parser;

import net.saifs.mathy.ast.*;
import net.saifs.mathy.evaluator.EvaluatorOperation;
import net.saifs.mathy.lexer.Lexer;
import net.saifs.mathy.token.Token;
import net.saifs.mathy.token.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private final Lexer lexer;
    private Token next;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public AbstractSyntaxTreeNode read(String contents) {
        this.lexer.read(contents);
        this.next = this.lexer.next();
        return this.expression();
    }

    public Token eat(TokenType...types) {
        Token token = this.next;

        if (token == null) {
            throw new RuntimeException("unexpected end of input");
        }

        if (!List.of(types).contains(token.getType())) {
            throw new RuntimeException("expected " + token.getType());
        }

        this.next = this.lexer.next();
        return token;
    }

    public boolean is(TokenType...types) {
        if (this.next == null) {
            return false;
        }
        return List.of(types).contains(this.next.getType());
    }

    public AbstractSyntaxTreeNode expression() {
        AbstractSyntaxTreeNode left = this.term();

        while (this.is(TokenType.PLUS, TokenType.MINUS)) {
            Token token = this.eat(TokenType.PLUS, TokenType.MINUS);
            EvaluatorOperation operation = switch (token.getType()) {
                case PLUS -> EvaluatorOperation.PLUS;
                case MINUS -> EvaluatorOperation.MINUS;
                default -> throw new RuntimeException("unexpected token for add/minus: " + token.getToken() + " with type " + token.getType());
            };
            left = new ASTBinaryNode(left, this.term(), operation);
        }

        return left;
    }

    public AbstractSyntaxTreeNode term() {
        AbstractSyntaxTreeNode left = this.exponential();

        while (this.is(TokenType.MUL, TokenType.DIV)) {
            Token token = this.eat(TokenType.MUL, TokenType.DIV);
            EvaluatorOperation operation = switch (token.getType()) {
                case MUL -> EvaluatorOperation.MUL;
                case DIV -> EvaluatorOperation.DIV;
                default -> throw new RuntimeException("unexpected token for mul/div: " + token.getToken() + " with type " + token.getType());
            };
            left = new ASTBinaryNode(left, this.exponential(), operation);
        }

        return left;
    }

    public AbstractSyntaxTreeNode exponential() {
        AbstractSyntaxTreeNode left = this.call();

        while (this.is(TokenType.EXP)) {
            this.eat(TokenType.EXP);
            left = new ASTBinaryNode(left, this.call(), EvaluatorOperation.EXP);
        }

        return left;
    }

    public AbstractSyntaxTreeNode call() {
        AbstractSyntaxTreeNode callee = basic();

        if (callee instanceof ASTIdentifierNode ident) {
            if (this.is(TokenType.NUMBER, TokenType.IDENT)) {
                return new ASTCallNode(ident.getIdentifier(), List.of(this.call()));
            }

            if (this.is(TokenType.LBRACKET)) {
                this.eat(TokenType.LBRACKET);
                List<AbstractSyntaxTreeNode> args = new ArrayList<>();

                args.add(this.expression());

                while (this.is(TokenType.COMMA)) {
                    this.eat(TokenType.COMMA);
                    args.add(this.expression());
                }

                this.eat(TokenType.RBRACKET);
                return new ASTCallNode(ident.getIdentifier(), args);
            }
        }

        return callee;
    }

    public AbstractSyntaxTreeNode basic() {
        if (this.is(TokenType.LBRACKET)) {
            this.eat(TokenType.LBRACKET);
            AbstractSyntaxTreeNode expr = this.expression();
            this.eat(TokenType.RBRACKET);
            return expr;
        }

        if (this.is(TokenType.NUMBER)) {
            Token token = this.eat(TokenType.NUMBER);
            return new ASTNumberNode(Double.parseDouble(token.getToken()));
        }

        if (this.is(TokenType.IDENT)) {
            Token token = this.eat(TokenType.IDENT);
            return new ASTIdentifierNode(token.getToken());
        }

        throw new RuntimeException("unexpected invalid input: " + this.next);
    }
}
