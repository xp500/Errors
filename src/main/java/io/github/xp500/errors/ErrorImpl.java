package io.github.xp500.errors;

import io.github.xp500.errors.ReturnElseExecutor.NoActionReturnElseExecutor;
import io.github.xp500.errors.VoidElseExecutor.ConsumerActionVoidElseExecutor;
import io.github.xp500.errors.VoidElseExecutor.NoActionVoidElseExecutor;
import io.github.xp500.errors.functional.FunctionWithReturnValue;
import io.github.xp500.errors.functional.OneArgFunction;

import java.util.function.Consumer;

public abstract class ErrorImpl<E, C> implements ErrorOr<E, C> {

    private final E error;

    public ErrorImpl(final E error) {
	this.error = error;
    }

    @Override
    public VoidElseExecutor<C> ifError(final Consumer<E> consumer) {
	consumer.accept(error);
	return new NoActionVoidElseExecutor<>();
    }

    @Override
    public <T> ReturnElseExecutor<T, ? extends FunctionWithReturnValue<T>> ifErrorReturn(final OneArgFunction<E, T> function) {
	return new NoActionReturnElseExecutor<>(function.apply(error));
    }

    @Override
    public VoidElseExecutor<Consumer<E>> ifNotError(final C consumer) {
	return new ConsumerActionVoidElseExecutor<>(error);
    }

}
