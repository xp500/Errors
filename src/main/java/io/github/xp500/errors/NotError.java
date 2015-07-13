package io.github.xp500.errors;

import io.github.xp500.errors.VoidElseExecutor.NoActionVoidElseExecutor;

import java.util.function.Consumer;

public abstract class NotError<E, C> implements ErrorOr<E, C> {

    @Override
    public VoidElseExecutor<Consumer<E>> ifNotError(final C consumer) {
	doAction(consumer);
	return new NoActionVoidElseExecutor<>();
    }

    protected abstract void doAction(final C consumer);

}
