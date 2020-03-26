package io.idx;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.IOUtils;

public class IDX1UByteReader implements Closeable {

	private static final int MAGIC_NUMBER = 0x00000801;

	private final InputStream source;

	private boolean closed = false;

	public IDX1UByteReader(InputStream stream) {
		this.source = stream;
	}

	public IDX1UByteReader(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	public byte[] read() throws IOException {
		int magic = IOUtils.readInt(source);
		if (magic != MAGIC_NUMBER)
			throw new IOException("Wrong Magic Number: " + magic + "!=" + MAGIC_NUMBER);

		int size = IOUtils.readInt(source);

		byte[] data = new byte[size];

		if (source.read(data) < size)
			throw new EOFException();

		return data;
	}

	@Override
	public void close() throws IOException {
		if (closed || !(source instanceof Closeable))
			return;
		((Closeable) source).close();
	}

}
