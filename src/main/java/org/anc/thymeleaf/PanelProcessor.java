package org.anc.thymeleaf;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Keith Suderman
 */
public class PanelProcessor  extends AbstractMarkupSubstitutionElementProcessor
{
	private String type;
	private int precedence;

	public PanelProcessor(String type)
	{
		super(type);
		this.type = type;
		this.precedence = 10000;
	}

	public void setPrecedence(int precedence)
	{
		this.precedence = precedence;
	}

	@Override
	public int getPrecedence()
	{
		return precedence;
	}

	@Override
	protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element)
	{
//		String heading = element.getAttributeValue("heading");
//		String footer = element.getAttributeValue("footer");
//		if (heading != null || footer != null)
//		{
//			final Configuration configuration = arguments.getConfiguration();
//			final IStandardExpressionParser parser =
//					  StandardExpressions.getExpressionParser(configuration);
//			if (heading != null)
//			{
//				final IStandardExpression expression =
//						  parser.parseExpression(configuration, arguments, heading);
//				heading = (String) expression.execute(configuration, arguments);
//			}
//			if (footer != null)
//			{
//				final IStandardExpression expression =
//						  parser.parseExpression(configuration, arguments, footer);
//				footer = (String) expression.execute(configuration, arguments);
//			}
//		}
		String heading = getHeading(arguments, element);
		String footer = getFooter(arguments, element);
		Element panel = div("panel panel-" + type);
		panel.setProcessable(true);
		if (heading != null)
		{
			Element header = div("panel-heading");
			header.addChild(new Text(heading));
			panel.addChild(header);
		}

		Element body = div("panel-body");
		for (Node child : element.getChildren())
		{
			child.setProcessable(true);
			body.addChild(child);
		}
		panel.addChild(body);
		if (footer != null)
		{
			Element footerElement = div("panel-footer");
			footerElement.addChild(new Text(footer));
			panel.addChild(footerElement);
		}
		List<Node> nodes = new ArrayList<>(1);
		nodes.add(panel);
		return nodes;
	}

	private String getHeading(Arguments arguments, Element element)
	{
		String heading = element.getAttributeValue("heading");
		if (isExpression(heading))
		{
			return parse(arguments, heading);
		}
		return heading;
	}

	private String getFooter(Arguments arguments, Element element)
	{
		String footer = element.getAttributeValue("th:footer");
		if (isExpression(footer))
		{
			return parse(arguments, footer);
		}
		return footer;
	}

	private boolean isExpression(String input)
	{
		if (input == null)
		{
			return false;
		}
		if (input.length() < 4)
		{
			return false;
		}
		char c1 = input.charAt(0);
		char c2 = input.charAt(1);
		char last = input.charAt(input.length() - 1);
		if (c1 == '$' && c2 == '{') return last == '}';
		if (c1 == '@' && c2 == '{') return last == '}';
		if (c1 == '#' && c2 == '{') return last == '}';
		return false;
	}

	private String parse(Arguments arguments, String input)
	{
		final Configuration configuration = arguments.getConfiguration();
		final IStandardExpressionParser parser =
				  StandardExpressions.getExpressionParser(configuration);
		if (input != null)
		{
			final IStandardExpression expression =
					  parser.parseExpression(configuration, arguments, input);
			return (String) expression.execute(configuration, arguments);
		}
		return input;
	}

	private Element div(String classType)
	{
		Element element = new Element("div");
		element.setAttribute("class", classType);
		return element;
	}
}
