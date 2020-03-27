package io;

import java.io.File;
import java.io.IOException;

import core.data.Data;
import core.data.MNISTData;
import io.idx.IDX1UByteReader;
import io.idx.IDX3UByteReader;

public class MNISTLoader {

	private File dir;

	public MNISTLoader(File dir) {
		this.dir = dir;
	}

	public Data load(String name) throws IOException {
		File images = new File(dir, name + "-images.idx3-ubyte");
		File labels = new File(dir, name + "-labels.idx1-ubyte");
		
		return load(images, labels);
	}

	public Data load(File imgs, File lbls) throws IOException {
		byte[][][] imgArr;
		byte[]     lblArr;

		try (IDX3UByteReader reader = new IDX3UByteReader(imgs)) {
			imgArr = reader.read();
		}
		try (IDX1UByteReader reader = new IDX1UByteReader(lbls)) {
			lblArr = reader.read();
		}

		return new MNISTData(imgArr, lblArr);
	}

}
