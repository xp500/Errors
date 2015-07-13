package io.github.xp500.errors;

import static org.assertj.core.api.StrictAssertions.assertThat;
import io.github.xp500.errors.Errors.ErrorOrVoid;
import io.github.xp500.errors.functional.Command;
import io.github.xp500.errors.functional.OneArgFunction;
import io.github.xp500.errors.functional.Supplier;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ErrorOrVoidTest {

	private static enum TestError {
		ERROR1
	}

	private static final int IF_ERROR_NUM = 10;
	private static final int IF_NOT_ERROR_NUM = 2;

	private final ErrorOrVoid<TestError> errorOr;
	private final int expected;
	private final Command consumer;
	private final Supplier<Integer> f;
	private final DummyMutableClass d;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { Errors.forValue(5),
			IF_NOT_ERROR_NUM,
			(Function<DummyMutableClass, Consumer<Integer>>) d -> v -> d.setI(IF_NOT_ERROR_NUM),
			(OneArgFunction<Integer, Integer>) v -> IF_NOT_ERROR_NUM },
			{ Errors.forVoidError(TestError.ERROR1),
				IF_ERROR_NUM,
				(Function<DummyMutableClass, Command>) d -> () -> d.setI(IF_NOT_ERROR_NUM),
				(Supplier<Integer>) () -> IF_NOT_ERROR_NUM } });
	}

	public ErrorOrVoidTest(
			final ErrorOrVoid<TestError> errorOr, final int expected,
			final Function<DummyMutableClass, Command> consumerExtractor, final Supplier<Integer> f) {
		this.errorOr = errorOr;
		this.expected = expected;
		d = new DummyMutableClass();
		this.consumer = consumerExtractor.apply(d);
		this.f = f;
	}

	@Test
	public void testIfError() {
		errorOr.ifError(e -> d.setI(IF_ERROR_NUM)).otherwise(consumer);
		assertThat(d.i).isEqualTo(expected);
	}

	@Test
	public void testIfNotError() {
		errorOr.ifNotError(consumer).otherwise(e -> d.setI(IF_ERROR_NUM));
		assertThat(d.i).isEqualTo(expected);
	}

	@Test
	public void testIfErrorReturn() {
		errorOr.ifErrorReturn(e -> IF_ERROR_NUM).otherwise(f);
		assertThat(d.i).isEqualTo(expected);
	}

	private static class DummyMutableClass {

		private int i;

		public void setI(int i) {
			this.i = i;
		}
	}

}
