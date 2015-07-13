package io.github.xp500.errors;

import io.github.xp500.errors.functional.FunctionWithReturnValue;
import io.github.xp500.errors.functional.OneArgFunction;

import java.util.function.Consumer;

public interface ErrorOr<E, C> {

    /**
     * Performs the action described by the consumer passing the {@code Error}
     * as the argument, if {@code this} object is an error
     * 
     * @param consumer
     *            the action to be performed if {@code this} object is an error.
     * @return a {@link VoidElseExecutor<C>} that allows chaining for
     *         {@code else} actions.
     */
    public abstract VoidElseExecutor<C> ifError(final Consumer<E> consumer);

    public abstract <T, F extends FunctionWithReturnValue<T>> ReturnElseExecutor<T, F> ifErrorReturn(final OneArgFunction<E, T> function);

    /**
     * Performs the action described by the consumer if {@code this} object is
     * not an error.
     * 
     * <p>
     * The arguments passed to the consumer depend on how this interface is
     * implemented.
     * 
     * @param consumer
     *            the action to be performed if {@code this} object is not an
     *            error.
     * @return a {@link VoidElseExecutor<C>} that allows chaining for
     *         {@code else} actions.
     */
    public abstract VoidElseExecutor<Consumer<E>> ifNotError(final C consumer);

}
