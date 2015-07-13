package com.cred.utils;


public abstract class ErrorOr<E, T> {
    
    /**
     * Creates a {@code ErrorOr<E, T>} object containing an error.
     * 
     * @param error
     *            the error with which the {@code ErrorOr<E, T>} object will be
     *            initialized.
     * @return a {@code ErrorOr<E, T>} object containing the given error.
     */
    public static <E, T> ErrorOr<E, T> forError(final E error) {
	return new ErrorImpl<>(error);
    }

    /**
     * Creates a {@code ErrorOr<E, T>} object containing a value.
     * 
     * @param value
     *            the value with which the {@code ErrorOr<E, T>} object will be
     *            initialized.
     * @return a {@code ErrorOr<E, T>} object containing the given value.
     */
    public static <E, T> ErrorOr<E, T> forValue(final T value) {
	return new ValueImpl<>(value);
    }

    /**
     * Creates a {@code ErrorOr<E, T>} object with no value, but that is not an
     * error. This factory method can be used when a method should return
     * {@code void} in case of success. In these cases,
     * {@link ErrorOr#getValueOrFail()()} shouldn't be called or an exception
     * will be raised.
     * 
     * @return a {@code ErrorOr<E, T>} object representing {@code void}.
     */
    public static <E, T> ErrorOr<E, T> forVoid() {
	return new VoidImpl<>();
    }

    /**
     * Gets the value of this {@code ErrorOr<E, T>} object if it's a value
     * object. If this object is an error object then this method will throw an
     * {@link IllegalStateException}. Therefore, {@link ErrorOr#isError()}
     * should be called before calling this method.
     * 
     * @return the value that this object contains.
     * @throws IllegalStateException
     *             if this is an error object.
     */
    public abstract T getValueOrFail();

    /**
     * Determines whether this is an error or a value object.
     * 
     * @return {@code true} if this is an error object.
     */
    public abstract boolean isError();
    
    /**
     * Gets the error of this {@code ErrorOr<E, T>} object if it's an error
     * object. If this object is a value object then this method will throw an
     * {@link IllegalStateException}. Therefore, {@link ErrorOr#isError()}
     * should be called before calling this method.
     * 
     * @return the error that this object contains.
     * @throws IllegalStateException
     *             if this is a value object.
     */
    public abstract E getErrorOrFail();

    private static class ErrorImpl<E, T> extends ErrorOr<E, T> {

	private final E error;

	private ErrorImpl(final E error) {
	    this.error = error;
	}

	@Override
	public T getValueOrFail() {
	    throw new IllegalStateException(
		    "Tried to access the value of an error object. Have you called isError() first?");
	}

	@Override
	public boolean isError() {
	    return true;
	}

	@Override
	public E getErrorOrFail() {
	    return error;
	}

    }

    private static class ValueImpl<E, T> extends ErrorOr<E, T> {

	private final T value;

	private ValueImpl(final T value) {
	    this.value = value;
	}

	@Override
	public T getValueOrFail() {
	    return value;
	}

	@Override
	public boolean isError() {
	    return false;
	}

	@Override
	public E getErrorOrFail() {
	    throw new IllegalStateException(
		    "Tried to access the error of a value object. Have you called isError() first?");
	}

    }

    private static class VoidImpl<E, T> extends ErrorOr<E, T> {

	private VoidImpl() {}

	@Override
	public T getValueOrFail() {
	    throw new IllegalStateException(
		    "Tried to access the value of a void object.");
	}

	@Override
	public boolean isError() {
	    return false;
	}

	@Override
	public E getErrorOrFail() {
	    throw new IllegalStateException(
		    "Tried to access the error of a value object. Have you called isError() first?");
	}

    }
}
