package org.anc.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Keith Suderman
 */
public class PanelDialect extends AbstractDialect
{
	private static final String[] TYPES = {
			  "default", "primary", "info", "success", "warning", "danger"
	};

	protected Set<IProcessor> processors;

	public PanelDialect()
	{
		processors = new HashSet<>();
		for (String definition : TYPES)
		{
			processors.add(new PanelProcessor(definition));
		}
	}

	@Override
	public String getPrefix()
	{
		return "panel";
	}

	@Override
	public Set<IProcessor> getProcessors()
	{
		return processors;
	}

}
