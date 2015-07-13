package io.github.xp500.errors;

import io.github.xp500.errors.functional.Command;

import java.util.function.Consumer;

public abstract class VoidElseExecutor<C> {

	public abstract void otherwise(final C otherwise);

	public void otherwiseDoNothing() {
	}

	static final class NoActionVoidElseExecutor<C1> extends VoidElseExecutor<C1> {

		@Override
		public void otherwise(final C1 otherwise) {
			// Does nothing.
		}

	}

	static final class ConsumerActionVoidElseExecutor<T> extends VoidElseExecutor<Consumer<T>> {

		private final T value;

		public ConsumerActionVoidElseExecutor(
				final T value) {
			this.value = value;
		}

		@Override
		public void otherwise(final Consumer<T> otherwise) {
			otherwise.accept(value);
		}

	}

	static final class CommandActionVoidElseExecutor extends VoidElseExecutor<Command> {

		@Override
		public void otherwise(final Command otherwise) {
			otherwise.doCommand();
		}

	}

}
