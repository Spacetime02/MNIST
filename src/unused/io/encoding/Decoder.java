package unused.io.encoding;

import java.io.IOException;
import java.io.InputStream;

public abstract class Decoder<T> {
	
	public abstract T decode(InputStream stream) throws IOException;
	
}
