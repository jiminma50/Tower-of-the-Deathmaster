package me.deathjockey.tod.dy;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLFile {

	private String loc;
	private File phys_f;
	
	private Document xmlDoc;
	
	public XMLFile(String loc) {
		this.loc = loc;
		phys_f = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "out" + loc);
		
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
		xmlDoc = docBuilder.parse(phys_f);
	}

	public String getPath() {
		return loc;
	}
	
	public File getFile() {
		return phys_f;
	}
	
	public Document asDocument() {
		return xmlDoc;
	}
}
