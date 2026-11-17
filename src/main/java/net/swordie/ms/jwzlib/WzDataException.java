package net.swordie.ms.jwzlib;

/**
 * The wz folder can't serve a .wz: the message says what to do about it, so it's printed without a stack trace.
 */
public final class WzDataException extends IllegalStateException {

	public WzDataException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
