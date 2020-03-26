package unused.io.encoding;

import java.io.InputStream;

public abstract class Encoder<T> {

	public abstract InputStream encodeNext(T t);

}
