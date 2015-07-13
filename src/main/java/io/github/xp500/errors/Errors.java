package io.github.xp500.errors;

import io.github.xp500.errors.ReturnElseExecutor.FunctionActionReturnElseExecutor;
import io.github.xp500.errors.ReturnElseExecutor.NoActionReturnElseExecutor;
import io.github.xp500.errors.ReturnElseExecutor.SupplierActionReturnElseExecutor;
import io.github.xp500.errors.VoidElseExecutor.CommandActionVoidElseExecutor;
import io.github.xp500.errors.VoidElseExecutor.ConsumerActionVoidElseExecutor;
import io.github.xp500.errors.functional.Command;
import io.github.xp500.errors.functional.OneArgFunction;
import io.github.xp500.errors.functional.Supplier;

import java.util.function.Consumer;

/**
 * Utility class with factory methods to create values or errors.
 *
 * @author Jorge
 *
 */
public final class Errors {

	/**
	 * Interface that represents {@link ErrorOr<E>} objects where the {@code or} is void.
	 *
	 * <p>
	 * The typical use case for this interface is when a method returns void. For example
	 *
	 * <pre>
	 * {@code
	 * public ErrorOrVoid<ErrorClass> onlyPositiveInts(final int i) {
	 *     if (i <= 0) {
	 *         return Errors.forVoidError(ErrorClass.error());
	 *     }
	 *     // Do something with your positive int.
	 *     return Errors.forVoid();
	 * }
	 * </pre>
	 *
	 * @author Jorge
	 *
	 * @param <E>
	 *            the type of the error objects.
	 */
	public interface ErrorOrVoid<E> extends ErrorOr<E, Command> {

		/**
		 * Executes the given function if {@code this} object is an error. This is expected to be used chaining an
		 * {@code otherwise()} so that the returned value of the whole operation is the return value of the given
		 * function if {@code this} object is an error or the return value of the otherwise if it's not.
		 *
		 * <p>
		 * For example
		 *
		 * <pre>
		 * {
		 * 	&#064;code
		 * 	int i = Errors.forVoid().ifErrorReturn(e -&gt; 0).otherwise(() -&gt; 1);
		 * }
		 * </pre>
		 *
		 * @param function
		 *            the function that will be executed if {@code this} object is an error.
		 * @return the {@link ReturnElseExecutor} that will be used to chain this expression.
		 */
		<T> ReturnElseExecutor<T, Supplier<T>> ifErrorReturn(final OneArgFunction<E, T> function);

		/**
		 * Executes the given function if {@code this} object is not an error. This is expected to be used chaining an
		 * {@code otherwise()} so that the returned value of the whole operation is the return value of the given
		 * function if {@code this} object is an error or the return value of the otherwise if it's not.
		 *
		 * <p>
		 * For example
		 *
		 * <pre>
		 * {
		 * 	&#064;code
		 * 	int i = Errors.forVoid().ifErrorReturn(e -&gt; 0).otherwise(() -&gt; 1);
		 * }
		 * </pre>
		 *
		 * @param function
		 *            the function that will be executed if {@code this} object is not an error.
		 * @return the {@link ReturnElseExecutor} that will be used to chain this expression.
		 */
		<R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final Supplier<R> f);
	}

	/**
	 * Interface that represents {@link ErrorOr<E>} objects where the {@code or} is an object of type {@code T}.
	 *
	 * <p>
	 * The typical use case for this interface is when a method returns an object, but there could be errors. For
	 * example
	 *
	 * <pre>
	 * {@code
	 * public ErrorOrValue<ErrorClass, Integer> onlyPositiveInts(final int i) {
	 *     if (i <= 0) {
	 *         return Errors.forValueError(ErrorClass.error());
	 *     }
	 *     // Do something with your positive int.
	 *     return Errors.forValue(newInt);
	 * }
	 * </pre>
	 *
	 * @author Jorge
	 *
	 * @param <E>
	 *            the type of the error objects.
	 * @param <T>
	 *            the type of the value objects.
	 */
	public interface ErrorOrValue<E, T> extends ErrorOr<E, Consumer<T>> {

		/**
		 * Executes the given function if {@code this} object is an error. This is expected to be used chaining an
		 * {@code otherwise()} so that the returned value of the whole operation is the return value of the given
		 * function if {@code this} object is an error or the return value of the otherwise if it's not.
		 *
		 * <p>
		 * For example
		 *
		 * <pre>
		 * {
		 * 	&#064;code
		 * 	int i = Errors.forVoid().ifErrorReturn(e -&gt; 0).otherwise(() -&gt; 1);
		 * }
		 * </pre>
		 *
		 * @param function
		 *            the function that will be executed if {@code this} object is an error.
		 * @return the {@link ReturnElseExecutor} that will be used to chain this expression.
		 */
		<T1> ReturnElseExecutor<T1, OneArgFunction<T, T1>> ifErrorReturn(final OneArgFunction<E, T1> function);

		/**
		 * Executes the given function if {@code this} object is not an error. This is expected to be used chaining an
		 * {@code otherwise()} so that the returned value of the whole operation is the return value of the given
		 * function if {@code this} object is an error or the return value of the otherwise if it's not.
		 *
		 * <p>
		 * For example
		 *
		 * <pre>
		 * {
		 * 	&#064;code
		 * 	int i = Errors.forVoid().ifErrorReturn(e -&gt; 0).otherwise(() -&gt; 1);
		 * }
		 * </pre>
		 *
		 * @param function
		 *            the function that will be executed if {@code this} object is not an error.
		 * @return the {@link ReturnElseExecutor} that will be used to chain this expression.
		 */
		<R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final OneArgFunction<T, R> f);
	}

	/**
	 * Creates a {@code ErrorOrValue<E, T>} object containing a value.
	 *
	 * @param value
	 *            the value with which the {@code ErrorOrValue<E, T>} object will be initialized.
	 * @return a {@code ErrorOrValue<E, T>} object containing the given value.
	 */
	public static <E, T> ErrorOrValue<E, T> forValue(final T value) {
		return new ValueImpl<>(value);
	}

	/**
	 * Creates an {@code ErrorOrVoid<E>} object with no value, but that is not an error. This factory method can be used
	 * when a method should return {@code void} in case of success.
	 *
	 * @return a {@code ErrorOrVoid<E>} object representing {@code void}.
	 */
	public static <E> ErrorOrVoid<E> forVoid() {
		return new VoidImpl<>();
	}

	/**
	 * Creates an {@code ErrorOrVoid<E>} object containing an error.
	 *
	 * @param error
	 *            the error with which the {@code ErrorOrVoid<E>} object will be initialized.
	 * @return an {@code ErrorOrVoid<E, T>} object containing the given error.
	 */
	public static <E> ErrorOrVoid<E> forVoidError(final E error) {
		return new VoidErrorImpl<E>(error);
	}

	/**
	 * Creates an {@code ErrorOrValue<E, T>} object containing an error.
	 *
	 * @param error
	 *            the error with which the {@code ErrorOrValue<E, T>} object will be initialized.
	 * @return an {@code ErrorOrValue<E, T>} object containing the given error.
	 */
	public static <E, T> ErrorOrValue<E, T> forValueError(final E error) {
		return new ValueErrorImpl<>(error);
	}

	private static final class ValueImpl<E, T> extends NotError<E, Consumer<T>> implements ErrorOrValue<E, T> {

		private final T value;

		private ValueImpl(
				final T value) {
			this.value = value;
		}

		@Override
		public VoidElseExecutor<Consumer<T>> ifError(final Consumer<E> consumer) {
			return new ConsumerActionVoidElseExecutor<>(value);
		}

		@Override
		protected void doAction(final Consumer<T> consumer) {
			consumer.accept(value);
		}

		@Override
		public <T1> ReturnElseExecutor<T1, OneArgFunction<T, T1>> ifErrorReturn(final OneArgFunction<E, T1> function) {
			return new FunctionActionReturnElseExecutor<>(value);
		}

		@Override
		public <R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final OneArgFunction<T, R> function) {
			return new NoActionReturnElseExecutor<R, OneArgFunction<E, R>>(function.apply(value));
		}

	}

	private static final class VoidImpl<E> extends NotError<E, Command> implements ErrorOrVoid<E> {

		@Override
		public VoidElseExecutor<Command> ifError(final Consumer<E> consumer) {
			return new CommandActionVoidElseExecutor();
		}

		@Override
		protected void doAction(final Command consumer) {
			consumer.doCommand();
		}

		@Override
		public <T> ReturnElseExecutor<T, Supplier<T>> ifErrorReturn(final OneArgFunction<E, T> function) {
			return new SupplierActionReturnElseExecutor<T>();
		}

		@Override
		public <R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final Supplier<R> function) {
			return new NoActionReturnElseExecutor<R, OneArgFunction<E, R>>(function.get());
		}

	}

	private static final class ValueErrorImpl<E, T> extends ErrorImpl<E, Consumer<T>> implements ErrorOrValue<E, T> {

		private ValueErrorImpl(
				final E error) {
			super(error);
		}

		@Override
		public <T1> ReturnElseExecutor<T1, OneArgFunction<T, T1>> ifErrorReturn(final OneArgFunction<E, T1> function) {
			return new NoActionReturnElseExecutor<>(function.apply(error));
		}

		@Override
		public <R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final OneArgFunction<T, R> function) {
			return new FunctionActionReturnElseExecutor<>(error);
		}
	}

	private static final class VoidErrorImpl<E> extends ErrorImpl<E, Command> implements ErrorOrVoid<E> {

		private VoidErrorImpl(
				final E error) {
			super(error);
		}

		@Override
		public <T> ReturnElseExecutor<T, Supplier<T>> ifErrorReturn(final OneArgFunction<E, T> function) {
			return new NoActionReturnElseExecutor<>(function.apply(error));
		}

		@Override
		public <R> ReturnElseExecutor<R, OneArgFunction<E, R>> ifNotErrorReturn(final Supplier<R> function) {
			return new FunctionActionReturnElseExecutor<>(error);
		}
	}

}
