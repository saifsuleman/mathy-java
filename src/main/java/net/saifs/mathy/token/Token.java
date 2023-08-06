package net.saifs.mathy.token;

public class Token {
    private final String token;
    private final TokenType type;

    public Token(String token, TokenType type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.type.toString() + "(" + this.token + ")";
    }
}
