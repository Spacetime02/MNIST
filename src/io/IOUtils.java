package io;

import java.io.IOException;
import java.io.InputStream;

public final class IOUtils {

	private IOUtils() {}

	public static byte readByte(InputStream stream) throws IOException {
		// @formatter:off
		return (byte) (
				(stream.read() <<  0)
				);
		// @formatter:on
	}

	public static short readShort(InputStream stream) throws IOException {
		// @formatter:off
		return (short) (
				(stream.read() <<  8) |
				(stream.read() <<  0)
				);
		// @formatter:on
	}

	public static int readInt(InputStream stream) throws IOException {
		// @formatter:off
		return (int) (
				(stream.read() << 24) |
				(stream.read() << 16) |
				(stream.read() <<  8) |
				(stream.read() <<  0)
				);
		// @formatter:on
	}

	public static long readLong(InputStream stream) throws IOException {
		// @formatter:off
		return (
				((long) stream.read() << 56) |
				((long) stream.read() << 48) |
				((long) stream.read() << 40) |
				((long) stream.read() << 32) |
				((long) stream.read() << 24) |
				((long) stream.read() << 16) |
				((long) stream.read() <<  8) |
				((long) stream.read() <<  0)
				);
		// @formatter:on
	}

}
