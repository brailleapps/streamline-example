package org.daisy.streamline.example.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.daisy.streamline.api.tasks.InternalTask;
import org.daisy.streamline.api.tasks.TaskGroup;
import org.daisy.streamline.api.tasks.TaskGroupFactory;
import org.daisy.streamline.api.tasks.TaskGroupInformation;
import org.daisy.streamline.api.tasks.TaskGroupSpecification;
import org.daisy.streamline.api.tasks.TaskSystemException;

public class HtmlFactory implements TaskGroupFactory {
	private final Set<TaskGroupInformation> information;
	
	public HtmlFactory() {
		Set<TaskGroupInformation> tmp = new HashSet<>();
		tmp.add(TaskGroupInformation.newConvertBuilder("html", "txt").build());
		information = Collections.unmodifiableSet(tmp);
	}

	@Override
	public boolean supportsSpecification(TaskGroupInformation specification) {
		return information.contains(specification);
	}

	@Override
	public TaskGroup newTaskGroup(TaskGroupSpecification specification) {
		return new TaskGroup() {
			@Override
			public List<InternalTask> compile(Map<String, Object> parameters) throws TaskSystemException {
				return Arrays.asList(new HtmlTask(parameters));
			}
		};
	}

	@Override
	public Set<TaskGroupInformation> listAll() {
		return information;
	}

}
