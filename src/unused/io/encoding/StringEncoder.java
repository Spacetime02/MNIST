package unused.io.encoding;

import java.nio.charset.Charset;

public class StringEncoder extends Encoder<String> {

	public StringEncoder() {
		this(0, -1);
	}
	
	public StringEncoder(int offset, int maxLength) {
		this(Charset.defaultCharset(), offset, maxLength);
	}
	
	public StringEncoder(Charset charset) {
		this(charset, 0, -1);
	}
	
	public StringEncoder(Charset charset, int offset, int maxLength) {
		
	}
	
	public StringEncoder(String charsetName) {
		this(Charset.forName(charsetName));
	}
	
	public StringEncoder(String charsetName, int offset, int maxLength) {
		
	}
	
}
