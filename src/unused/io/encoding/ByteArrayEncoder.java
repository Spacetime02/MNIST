package unused.io.encoding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class ByteArrayEncoder extends Encoder<byte[]> {

	private final int offset;

	private final int maxLength;

	public ByteArrayEncoder() {
		this(0, -1);
	}

	public ByteArrayEncoder(int offset, int maxLength) {
		this.offset = offset;
		this.maxLength = maxLength;
	}

	@Override
	public InputStream encodeNext(byte[] byteArray) {
		byte[] arr;
		if (offset >= byteArray.length)
			arr = new byte[] {};
		else if (maxLength < 0 || offset + maxLength > byteArray.length)
			arr = Arrays.copyOfRange(byteArray, offset, byteArray.length);
		else
			arr = Arrays.copyOfRange(byteArray, offset, offset + maxLength);
		return new ByteArrayInputStream(arr);
	}

}
