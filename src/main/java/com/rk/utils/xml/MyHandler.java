package com.rk.utils.xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
//		String tag = (String) tags.peek();
//		if (tag.equals("NO")) {
//			System.out.print("车牌号码：" + new String(ch, start, length));
//		}
//		if (tag.equals("ADDR")) {
//			System.out.println("地址:" + new String(ch, start, length));
//		}
	}
}
