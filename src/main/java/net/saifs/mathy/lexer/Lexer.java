package net.saifs.mathy.lexer;

import net.saifs.mathy.token.Token;
import net.saifs.mathy.token.TokenType;

import java.util.regex.Matcher;

public class Lexer {
    private int cursor = 0;
    private String contents = null;

    public void read(String contents) {
        this.cursor = 0;
        this.contents = contents;
    }

    public Token next() {
        if (this.cursor >= this.contents.length()) {
            return null;
        }

        String str = this.contents.substring(this.cursor);
        for (TokenType type : TokenType.values()) {
            Matcher matcher = type.getPattern().matcher(str);
            if (!matcher.find()) {
                continue;
            }
            String match = matcher.group();
            this.cursor += match.length();

            if (type == TokenType.NONE) {
                return this.next();
            }

            return new Token(match, type);
        }

        throw new RuntimeException("unrecognized input: " + str);
    }
}
