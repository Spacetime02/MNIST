package io.idx;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import io.IOUtils;

public class IDX3UByteReader implements Closeable {

	private static final int MAGIC_NUMBER = 0x00000803;

	private final InputStream source;

	private boolean closed = false;

	public IDX3UByteReader(InputStream stream) {
		this.source = stream;
	}

	public IDX3UByteReader(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	public byte[][][] read() throws IOException {
		int magic = IOUtils.readInt(source);
		if (magic != MAGIC_NUMBER)
			throw new IOException("Wrong Magic Number: " + magic + "!=" + MAGIC_NUMBER);

		int size1 = IOUtils.readInt(source);
		int size2 = IOUtils.readInt(source);
		int size3 = IOUtils.readInt(source);

		byte[][][] data1 = new byte[size1][size2][size3];

		for (byte[][] data2 : data1)
			for (byte[] data3 : data2)
				if (source.read(data3) < size3)
					throw new EOFException();

		return data1;
	}

	@Override
	public void close() throws IOException {
		if (closed || !(source instanceof Closeable))
			return;
		((Closeable) source).close();
	}

}
