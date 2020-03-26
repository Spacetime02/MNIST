package unused.util.tensor;

import java.util.List;

public interface Tensor<T> {
	
	public T get(int... indices);
	
	public T set(T value, int... indices);

	public int getRank();
	
	public int[] getDimensions();
	
	public Tensor<T> getChild(int index);
	
	public Tensor<T> flatten();
	
	public Object toArray();
	
	public Object toPrimitiveArray();
	
	public List<?> toList();
	
	public default boolean isScalar() {
		return getRank() == 0;
	}
	
	public default boolean isVector() {
		return getRank() == 1;
	}
	
	public default boolean isMatrix() {
		return getRank() == 2;
	}
	
}
