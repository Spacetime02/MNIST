package core.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;

import util.function.IntToDoubleTriFunction;

public class FeedForwardNetworkBuilder {

	public static IntToDoubleTriFunction gaussianWeightInitializer() {
		return gaussianWeightInitializer(new Random());
	}

	public static IntToDoubleTriFunction gaussianWeightInitializer(Random randy) {
		return noParameterWeightInitializer(randy::nextGaussian);
	}

	public static IntToDoubleTriFunction noParameterWeightInitializer(DoubleSupplier supplier) {
		return (layerIndex, index, nextIndex) -> supplier.getAsDouble();
	}

	private Integer                        outputSize          = null;
	private final List<Integer>            layerSizes          = new ArrayList<>();
	private final List<ActivationFunction> activationFunctions = new ArrayList<>();
	private Double                         learningRate        = null;
	private IntToDoubleTriFunction         weightInitializer   = null;
	private String                         name                = null;

	public FeedForwardNetworkBuilder addLayer(int layerSize, ActivationFunction activationFunction) {
		layerSizes.add(layerSize);
		activationFunctions.add(activationFunction);
		return this;
	}

	public FeedForwardNetworkBuilder addLayer(int layerSize) {
		return addLayer(layerSize, null);
	}

	public FeedForwardNetworkBuilder setLayerSize(int layerIndex, int layerSize) {
		layerSizes.set(layerIndex, layerSize);
		return this;
	}

	public FeedForwardNetworkBuilder setActivationFunction(int layerIndex, ActivationFunction activationFunction) {
		activationFunctions.set(layerIndex, activationFunction);
		return this;
	}

	public FeedForwardNetworkBuilder setOutputSize(int outputSize) {
		this.outputSize = outputSize;
		return this;
	}

	public FeedForwardNetworkBuilder setLearningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}

	public FeedForwardNetworkBuilder setWeightInitializer(IntToDoubleTriFunction weightInitializer) {
		this.weightInitializer = weightInitializer;
		return this;
	}

	public FeedForwardNetworkBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public FeedForwardNetwork build() {
		if (outputSize == null)
			throw new IllegalStateException("The output size has not been set.");
		if (learningRate == null)
			throw new IllegalStateException("The learning rate has not been set.");
		if (weightInitializer == null)
			throw new IllegalStateException("The weight generator has not been set.");
		int nullIndex = activationFunctions.indexOf(null);
		if (nullIndex >= 0)
			throw new IllegalStateException("The activation function at index " + nullIndex + " has not been set.");
		if (name == null)
			throw new IllegalStateException("The name has not been set.");
		int   numLayers    = layerSizes.size();
		int[] layerSizeArr = new int[numLayers];
		for (int layerIndex = 0; layerIndex < numLayers; layerIndex++)
			layerSizeArr[layerIndex] = layerSizes.get(layerIndex);
		ActivationFunction[] activationFunctionArr = activationFunctions.toArray(new ActivationFunction[numLayers]);
		return new FeedForwardNetwork(name, weightInitializer, layerSizeArr, outputSize, activationFunctionArr, learningRate);
	}

}
