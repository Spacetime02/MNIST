package unused.util.tensor;

import java.util.Arrays;
import java.util.List;

public class MutableTensor<E> implements Tensor<E> {

	private final int rank;

	private final int[] dimensions;

	public MutableTensor(int... dimensions) {
		rank = dimensions.length;
		this.dimensions = Arrays.copyOf(dimensions;
	}

	@Override
	public E get(int... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(E value, int... indices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getDimensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tensor<E> getChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tensor<E> flatten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toPrimitiveArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> toList() {
		// TODO Auto-generated method stub
		return null;
	}

}
