package me.deathjockey.tod.dy;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLFile {

	private String loc;
	
	private Document xmlDoc;
	
	public XMLFile(String loc) {
		this.loc = loc;
		
		try {
			init();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		xmlDoc.normalize();
	}
	
	private void init() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = builder.newDocumentBuilder();
		xmlDoc = docBuilder.parse(this.getClass().getResourceAsStream(loc));
	}

	public String getPath() {
		return loc;
	}
	
	public Document asDocument() {
		return xmlDoc;
	}
}
