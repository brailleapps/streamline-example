package org.daisy.streamline.example;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.daisy.streamline.api.media.DefaultAnnotatedFile;
import org.daisy.streamline.api.tasks.CompiledTaskSystem;
import org.daisy.streamline.api.tasks.TaskSystemException;
import org.daisy.streamline.api.tasks.TaskSystemFactoryException;
import org.daisy.streamline.api.tasks.TaskSystemFactoryMaker;
import org.daisy.streamline.engine.RunnerResult;
import org.daisy.streamline.engine.TaskRunner;

/**
 * Main class
 * @author Joel Håkansson
 */
public class Main {

	/**
	 * Starts the application.
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {
			CompiledTaskSystem cts = TaskSystemFactoryMaker.newInstance().newTaskSystem("md", "txt", "en").compile(Collections.emptyMap());
			List<RunnerResult> results = new TaskRunner.Builder("Test").build()
				.runTasks(DefaultAnnotatedFile.with(Paths.get("readme.md")).build(), new File("out.txt"), cts);
			printOptions(results);
		} catch (IOException | TaskSystemException | TaskSystemFactoryException e) {
			Logger logger = Logger.getLogger(Main.class.getCanonicalName());
			logger.log(Level.INFO, "Something went wrong.", e);
		}
	}
	
	private static void printOptions(List<RunnerResult> results) {
		System.out.println("Options: ");
		results.stream().map(r->r.getTask().getName() + "\n\t" + 
					r.getTask().getOptions().stream().map(o->o.getKey() + " (" + o.getDefaultValue()+ ")"+ "\n\t\t" + o.getDescription()).collect(Collectors.joining("\n\t"))
				).forEach(System.out::println);
	}

}
