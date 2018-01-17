package com.wondertek.core.util;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.wondertek.mobilevideo.core.util.XmlUtil;

public class XmlUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsAvalidXML() {
		fail("Not yet implemented");
	}

	@Test
	public void testOpenXml() throws ParserConfigurationException, SAXException, IOException {
		String file = this.getClass().getResource("/data/119.xml").getPath();
		Document doc = XmlUtil.openXml(file);
		assertNotNull(doc);
		Element root = doc.getDocumentElement();
		assertNotNull(root);
	}

	@Test
	public void testGetDocByStr() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStrByTag() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElementText() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateXml() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateXmlToFileStringMapOfStringObjectString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateXmlToFileStringMapOfStringObjectStringString() {
		fail("Not yet implemented");
	}

}
