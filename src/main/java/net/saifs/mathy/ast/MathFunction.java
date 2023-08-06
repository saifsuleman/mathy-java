package net.saifs.mathy.ast;

@FunctionalInterface
public interface MathFunction {
    double apply(Double... args);
}

