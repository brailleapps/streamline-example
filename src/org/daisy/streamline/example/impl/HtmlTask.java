package org.daisy.streamline.example.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.daisy.streamline.api.media.AnnotatedFile;
import org.daisy.streamline.api.media.DefaultAnnotatedFile;
import org.daisy.streamline.api.tasks.InternalTaskException;
import org.daisy.streamline.api.tasks.ReadWriteTask;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

class HtmlTask extends ReadWriteTask {
	
	HtmlTask(Map<String, Object> params) {
		super("HTML to text");
	}

	@Override
	public void execute(File input, File output) throws InternalTaskException {
		execute(new DefaultAnnotatedFile.Builder(input).build(), output);
	}

	@Override
	public AnnotatedFile execute(AnnotatedFile input, File output) throws InternalTaskException {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document d = db.parse(input.getFile());
			Files.write(output.toPath(), d.getElementsByTagName("body").item(0).getTextContent().getBytes(StandardCharsets.UTF_8));
			return new DefaultAnnotatedFile.Builder(output).extension("txt").mediaType("text/plain").build();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new InternalTaskException(e);
		}
	}
}
