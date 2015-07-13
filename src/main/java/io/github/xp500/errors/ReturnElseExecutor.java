package io.github.xp500.errors;

import io.github.xp500.errors.functional.OneArgFunction;
import io.github.xp500.errors.functional.Supplier;

public abstract class ReturnElseExecutor<T, F> {

    public abstract T otherwise(final F function);

    public T otherwiseReturnNull() {
	return null;
    }

    static final class NoActionReturnElseExecutor<T1, F1> extends
	    ReturnElseExecutor<T1, F1> {

	private final T1 retValue;

	NoActionReturnElseExecutor(final T1 retValue) {
	    this.retValue = retValue;
	}

	@Override
	public T1 otherwise(final F1 function) {
	    return retValue;
	}

    }

    static final class SupplierActionReturnElseExecutor<T> extends ReturnElseExecutor<T, Supplier<T>> {

	@Override
	public T otherwise(final Supplier<T> function) {
	    return function.get();
	}

    }

    static final class FunctionActionReturnElseExecutor<T, R> extends ReturnElseExecutor<R, OneArgFunction<T, R>> {

	private final T value;

	public FunctionActionReturnElseExecutor(final T value) {
	    this.value = value;
	}

	@Override
	public R otherwise(final OneArgFunction<T, R> function) {
	    return function.apply(value);
	}

    }

}
