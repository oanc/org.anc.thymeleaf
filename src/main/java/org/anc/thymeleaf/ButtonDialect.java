package org.anc.thymeleaf;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Keith Suderman
 */
@Component
public class ButtonDialect extends AbstractDialect
{
	private static final String[][] BUTTON_DEFINITIONS = {
			  {"back", "primary", "backward"},
			  {"ban", "danger", "ban-circle"},
			  {"clock", "primary", "time"},
			  {"delete", "danger", "trash"},
			  {"edit", "success", "edit"},
			  {"file", "primary", "file"},
			  {"home", "primary", "home"},
			  {"lock", "warning", "lock"},
			  {"new", "success", "plus"},
			  {"ok", "success", "ok"},
			  {"cancel", "danger", "remove"},
			  {"pencil", "primary", "pencil"},
			  {"remove", "danger", "remove"},
			  {"repeat", "primary", "repeat"},
			  {"time", "primary", "time"},
			  {"success", "success", null},
			  {"primary", "primary", null},
			  {"default", "default", null},
			  {"warning", "warning", null},
			  {"danger", "danger", null}
	};

	protected Set<IProcessor> processors;

	public ButtonDialect()
	{
		processors = new HashSet<>();
		for (String[] definition : BUTTON_DEFINITIONS)
		{
			processors.add(new ButtonProcessor(definition));
		}
		processors.add(new CustomButtonProcessor());
	}

	@Override
	public String getPrefix()
	{
		return "button";
	}

	@Override
	public Set<IProcessor> getProcessors()
	{
		return processors;
	}

}
