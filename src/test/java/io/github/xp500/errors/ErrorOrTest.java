package com.cred.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;

public class ErrorOrTest {

    private static enum TestError {
	ERROR1
    }

    private ErrorOr<TestError, Integer> value;
    private ErrorOr<TestError, Integer> error;
    private ErrorOr<TestError, Void> voidValue;

    @Before
    public void setUp() {
	error = ErrorOr.forError(TestError.ERROR1);
	value = ErrorOr.forValue(5);
	voidValue = ErrorOr.forVoid();
    }

    @Test
    public void testIsErrorWithErrorObject() {
	assertThat(error.isError()).isTrue();
    }

    @Test
    public void testIsErrorWithValueObject() {
	assertThat(value.isError()).isFalse();
    }

    @Test
    public void testIsErrorWithVoidObject() {
	assertThat(voidValue.isError()).isFalse();
    }

    @Test
    public void testGetValueWithErrorObjectThrowsException() {
	assertThatThrownBy(() -> error.getValueOrFail()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testGetValueWithValueObject() {
	assertThat(value.getValueOrFail()).isEqualTo(5);
    }

    @Test
    public void testGetValueWithVoidObjectThrowsException() {
	assertThatThrownBy(() -> voidValue.getValueOrFail()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testGetErrorWithErrorObject() {
	assertThat(error.getErrorOrFail()).isEqualTo(TestError.ERROR1);
    }

    @Test
    public void testGetErrorWithValueObjectThrowsException() {
	assertThatThrownBy(() -> value.getErrorOrFail()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testGetErrorWithVoidObjectThrowsException() {
	assertThatThrownBy(() -> voidValue.getErrorOrFail()).isInstanceOf(IllegalStateException.class);
    }

}
