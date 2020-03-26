package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

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
		File imgs = new File(dir, name + "-images.idx3-ubyte");
		File lbls = new File(dir, name + "-labels.idx1-ubyte");

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
