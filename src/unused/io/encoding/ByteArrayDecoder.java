package unused.io.encoding;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayDecoder extends Decoder<byte[]> {

	private final int maxLength;

	public ByteArrayDecoder() {
		this(-1);
	}

	public ByteArrayDecoder(int maxLength) {
		if (maxLength < 0)
			throw new NegativeArraySizeException();
		this.maxLength = maxLength;
	}

	public byte[] decode(InputStream stream) throws IOException {
		if (maxLength < 0)
			return stream.readAllBytes();
		else
			return stream.readNBytes(maxLength);
	}

}
