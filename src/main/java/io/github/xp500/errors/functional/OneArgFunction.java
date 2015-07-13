package io.github.xp500.errors.functional;

@FunctionalInterface
public interface OneArgFunction<T, R> extends FunctionWithReturnValue<R> {

    R apply(final T value);
}
