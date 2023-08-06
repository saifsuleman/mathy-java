package net.saifs.mathy.evaluator;

import org.apache.commons.math3.util.FastMath;

import java.util.function.BiFunction;

public enum EvaluatorOperation {
    PLUS(Double::sum),
    MINUS((a, b) -> a - b),
    MUL((a, b) -> a * b),
    DIV((a, b) -> a / b),
    EXP(FastMath::pow);

    private final BiFunction<Double, Double, Double> function;

    EvaluatorOperation(BiFunction<Double, Double, Double> function) {
        this.function = function;
    }

    public double apply(double a, double b) {
        return this.function.apply(a, b);
    }
}
