package util.function;

import java.util.function.DoubleUnaryOperator;

@FunctionalInterface
public interface IntToDoubleTriFunction {

	public double applyAsDouble(int value1, int value2, int value3);

	public default IntToDoubleTriFunction andThen(DoubleUnaryOperator after) {
		return (int value1, int value2, int value3) -> after.applyAsDouble(applyAsDouble(value1, value2, value3));
	}

}
