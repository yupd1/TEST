package com.wondertek.mobilevideo.core.util;

import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.validation.Validator;
import java.io.*;

public class XsdSchemaSaxValidator {
	public static boolean validateXml(String schemaName, String xmlName)
			throws IOException, FileNotFoundException, SAXParseException,
			SAXException {
		if (schemaName == null || xmlName == null) {
			System.out.println("schemaName and xmlName can not empty!");
			return false;
		}
		Schema schema = null;
		schema = loadSchema(schemaName);
		return validateXml(schema, xmlName);

	}

	private static boolean validateXml(Schema schema, String xmlName)
			throws IOException, FileNotFoundException, SAXParseException,
			SAXException {
		Validator validator = schema.newValidator();
		FileInputStream fis = new FileInputStream(xmlName);
		SAXSource source = new SAXSource(new InputSource(fis));
		validator.validate(source);
		return true;
	}

	private static Schema loadSchema(String name) throws SAXException {
		Schema schema = null;
//		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
//		SchemaFactory factory = SchemaFactory.newInstance(language);
//		schema = factory.newSchema(new File(name));
		return schema;
	}
}
