package io.github.xp500.errors.functional;

@FunctionalInterface
public interface Supplier<T> extends FunctionWithReturnValue<T> {

    T get();

}
