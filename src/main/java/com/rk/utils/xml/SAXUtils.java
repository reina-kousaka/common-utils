package com.rk.utils.xml;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX方式XML解析
 * @author ZhaoKang
 *
 */
public class SAXUtils {

	public static void parse() throws ParserConfigurationException, SAXException, IOException {
		long lasting = System.currentTimeMillis();
		SAXParserFactory sf = SAXParserFactory.newInstance();
		SAXParser sp = sf.newSAXParser();
		FileInputStream fis = new FileInputStream("d:/model/refresh/packages.18sinosoftbi_car_iqd.xml");
		DefaultHandler dh = new DefaultHandler();
		sp.parse(fis, dh);

		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting) + "毫秒");
	}

}
