package net.saifs.mathy;

import net.saifs.mathy.compiler.Compiler;

import java.util.HashMap;

public class Mathy {
    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        System.out.println(compiler.parse("7+8"));
    }
}
