package net.saifs.mathy.token;

import java.util.regex.Pattern;

public enum TokenType {
    NONE("^\\s+"),
    NUMBER("^-?\\d+(?:\\.\\d+)?"),
    IDENT("^[a-zA-Z]+"),
    PLUS("^\\+"),
    MINUS("^\\-"),
    MUL("^\\*"),
    DIV("^\\/"),
    EXP("^\\^"),
    LBRACKET("^\\("),
    RBRACKET("^\\)"),
    COMMA("^\\,");

    private final Pattern pattern;

    TokenType(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
