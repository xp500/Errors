package io.github.xp500.errors;

import io.github.xp500.errors.VoidElseExecutor.ConsumerActionVoidElseExecutor;
import io.github.xp500.errors.VoidElseExecutor.NoActionVoidElseExecutor;

import java.util.function.Consumer;

public abstract class ErrorImpl<E, C> implements ErrorOr<E, C> {

	protected final E error;

	public ErrorImpl(
			final E error) {
		this.error = error;
	}

	@Override
	public VoidElseExecutor<C> ifError(final Consumer<E> consumer) {
		consumer.accept(error);
		return new NoActionVoidElseExecutor<>();
	}

	@Override
	public VoidElseExecutor<Consumer<E>> ifNotError(final C consumer) {
		return new ConsumerActionVoidElseExecutor<>(error);
	}

}
