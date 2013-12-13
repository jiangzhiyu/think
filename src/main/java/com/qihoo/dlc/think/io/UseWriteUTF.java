package com.qihoo.dlc.think.io;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UseWriteUTF {
	public static void main(String[] args) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream("Data.txt")));
		out.writeUTF("hello");
		out.close();
	}
}
