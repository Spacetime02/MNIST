package core.nn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.data.Data;
import core.data.DataPoint;

public class MNISTTrainer {

	private final List<FeedForwardNetwork> trainees;

	private Data data;

	private int batchSize = 1;

	public MNISTTrainer(Data data, FeedForwardNetwork... trainees) {
		this(data, Arrays.asList(trainees));
	}

	public MNISTTrainer(Data data, List<FeedForwardNetwork> trainees) {
		this.data = data;
		this.trainees = new ArrayList<>(trainees);
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public int getDataSize() {
		return data.getSize();
	}

	public void shuffleData() {
		data.shuffle();
	}

	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * If the dataset is of size 0, this method returns the batch size.
	 */
	public int getFinalBatchSize() {
		int size = data.getSize() % batchSize;
		return size == 0 ? batchSize : size;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public int getBatchCount() {
		return (data.getSize() - getFinalBatchSize()) / batchSize + 1;
	}

	public List<MNISTTestResult[][]> trainEpochs(int epochCount) {
		int trCount  = trainees.size();
		int dataSize = data.getSize();

		List<MNISTTestResult[][]> allResults = new ArrayList<>(trCount);
		for (int epochIndex = 0; epochIndex < epochCount; epochIndex++)
			allResults.add(new MNISTTestResult[epochCount][]);

		if (dataSize == 0)
			return allResults;

		String ecStr  = Integer.toString(epochCount);
		String format = String.format("%%%dd/%s epochs%n", ecStr.length(), ecStr);
		for (int epochIndex = 0; epochIndex < epochCount; epochIndex++) {
			System.out.printf(format, epochIndex);
			List<MNISTTestResult[]> allEpochResults = trainEpoch();
			for (int trIndex = 0; trIndex < trCount; trIndex++) {
				MNISTTestResult[] epochResults = allEpochResults.get(trIndex);
				allResults.get(trIndex)[epochIndex] = epochResults;
			}
		}
		System.out.printf(format, epochCount);

		return allResults;
	}

	public List<MNISTTestResult[]> trainEpoch() {
		int trCount  = trainees.size();
		int dataSize = data.getSize();

		List<MNISTTestResult[]> allResults = new ArrayList<>(trCount);
		for (int trIndex = 0; trIndex < trCount; trIndex++)
			allResults.add(new MNISTTestResult[dataSize]);

		if (dataSize == 0)
			return allResults;

		int batchCount = getBatchCount();

		String bcStr  = Integer.toString(batchCount);
		String format = String.format("\t%%%dd/%s batches%n", bcStr.length(), bcStr);
		for (int batchIndex = 0; batchIndex < batchCount; batchIndex++) {
			System.out.printf(format, batchIndex);
			List<MNISTTestResult[]> allBatchResults = trainBatch(batchIndex);
			for (int trIndex = 0; trIndex < trCount; trIndex++) {
				MNISTTestResult[] batchResults = allBatchResults.get(trIndex);
				System.arraycopy(batchResults, 0, allResults.get(trIndex), batchIndex * batchSize, batchResults.length);
			}
		}
		System.out.printf(format, batchCount);

		return allResults;
	}

	public List<MNISTTestResult[]> trainBatch(int batchIndex) {
		return trainBatchFrom(batchSize * batchIndex);
	}

	public List<MNISTTestResult[]> trainBatchFrom(int start) {
		int trCount   = trainees.size();
		int dataSize  = data.getSize();
		int realStart = start < 0 ? 0 : start;
		int realEnd   = start + batchSize < dataSize ? start + batchSize : dataSize;
		int batLen    = realEnd - realStart;

		List<MNISTTestResult[]> allResults = new ArrayList<>(trainees.size());

		if (batLen == 0) {
			for (int trIndex = 0; trIndex < trCount; trIndex++)
				allResults.add(new MNISTTestResult[0]);
			return allResults;
		}

		DataPoint[] batch = new DataPoint[batLen];

		for (int iterIndex = 0; iterIndex < batch.length; iterIndex++)
			batch[iterIndex] = data.getDataPoint(iterIndex + realStart);

		for (FeedForwardNetwork trainee : trainees) {
			MNISTTestResult[] results = new MNISTTestResult[batLen];

			double[][] pred = trainee.train(batch);

			for (int iterIndex = 0; iterIndex < batLen; iterIndex++)
				results[iterIndex] = new MNISTTestResult(pred[iterIndex], batch[iterIndex].getOutput());

			allResults.add(results);
		}

		return allResults;
	}

}
