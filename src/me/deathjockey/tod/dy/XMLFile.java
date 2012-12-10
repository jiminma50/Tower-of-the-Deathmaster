package me.deathjockey.tod.dy;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLFile {

	private URL url;
	private File phys_f;
	
	private Document xmlDoc;
	
	public XMLFile(URL url) {
		this.url = url;
		
		try {
			phys_f = new File(url.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
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

	public URL getPath() {
		return url;
	}
	
	public File getFile() {
		return phys_f;
	}
	
	public Document asDocument() {
		return xmlDoc;
	}
}
