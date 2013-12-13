package com.qihoo.dlc.think.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {
	private static final int BSIZE = 1024;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		FileChannel fc = new FileOutputStream("data.txt").getChannel();
		fc.write(ByteBuffer.wrap("x中文".getBytes()));
		fc.close();
		fc = new RandomAccessFile("data.txt", "rw").getChannel();
		fc.position(fc.size());
		fc.write(ByteBuffer.wrap("some more".getBytes()));
		fc.close();
		fc = new FileInputStream("data.txt").getChannel();
		ByteBuffer buf = ByteBuffer.allocate(BSIZE);
		fc.read(buf);
		buf.flip();
		while (buf.hasRemaining()) {
			System.out.print((char) buf.get());
		}
	}
}
