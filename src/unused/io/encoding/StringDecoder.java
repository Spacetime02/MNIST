package unused.io.encoding;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class StringDecoder extends Decoder<String> {
	
	private final int maxLength

	private final CharsetDecoder charsetDecoder;

	private final ByteArrayDecoder byteArrayDecoder;

	public StringDecoder() {
		this(-1);
	}

	public StringDecoder(int maxLength) {
		this(Charset.defaultCharset(), maxLength);
	}

	public StringDecoder(Charset charset) {
		this(charset, -1);
	}

	public StringDecoder(Charset charset, int maxLength) {
		byteArrayDecoder = new ByteArrayDecoder(maxLength);
		charsetDecoder = charset.newDecoder();
	}

	public StringDecoder(String charsetName) {
		this(Charset.forName(charsetName));
	}

	public StringDecoder(String charsetName, int maxLength) {
		this(Charset.forName(charsetName), maxLength);
	}

	@Override
	public String decode(InputStream stream) throws IOException {
		byte[] arr = byteArrayDecoder.decode(stream);
		return charsetDecoder.decode(ByteBuffer.wrap(arr)).toString();
	}

}
