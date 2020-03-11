package com.rk.utils.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtils {

	public static byte[] toByte(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		byte[] b = baos.toByteArray();
		baos.close();
		oos.close();
		return b;
	}

	public static Object toObject(byte[] b) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		return obj;
	}
}
