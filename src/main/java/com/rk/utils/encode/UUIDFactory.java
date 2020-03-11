package com.rk.utils.encode;

import java.util.UUID;

public class UUIDFactory {

	public static void main(String args[]) {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		String sub = uuid.substring(0, uuid.indexOf("-"));
		uuid = uuid.replace(sub, sub.toUpperCase());
		sub = uuid.substring(uuid.lastIndexOf("-"));
		uuid = uuid.replace(sub, sub.toUpperCase());
		uuid = uuid.replaceFirst("-", "@");
		uuid = uuid.replaceFirst("-", "#");
		uuid = uuid.replaceFirst("-", "&");
		uuid = uuid.replaceFirst("-", "!");
		System.out.println(uuid);
	}
}
