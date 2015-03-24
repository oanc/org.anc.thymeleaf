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
public class CustomButtonProcessor  extends AbstractMarkupSubstitutionElementProcessor
{
	public CustomButtonProcessor()
	{
		super("custom");
	}

	@Override
	public int getPrecedence()
	{
		return 10000;
	}

	@Override
	protected List<Node> getMarkupSubstitutes(Arguments arguments, Element element)
	{
		// The 'href' attribute can contains a Spring EL expression, e.g.
		// 	href="${resource.id}"
		// so we need to create an expression parser that can evaluate the
		// expression and return a meaningful result.
		String href = element.getAttributeValue("href");
		if (href == null)
		{
			href = "${'#'}";
		}

		String typeAtt = element.getAttributeValue("type");
		if (typeAtt == null)
		{
			typeAtt = "primary";
		}
		String size = element.getAttributeValue("size");
		String classAtt;
		if (size != null)
		{
			classAtt = String.format("btn btn-%s btn-%s", size, typeAtt);
		}
		else
		{
			classAtt = "btn btn-" + typeAtt;
		}
		final Configuration configuration = arguments.getConfiguration();
		final IStandardExpressionParser parser =
				  StandardExpressions.getExpressionParser(configuration);
		final IStandardExpression expression =
				  parser.parseExpression(configuration, arguments, href);
		href =  (String) expression.execute(configuration, arguments);

		List<Node> children = element.getChildren();
		Element button = new Element("a");
		button.setAttribute("class", classAtt);
		button.setAttribute("href", href);

		String icon = element.getAttributeValue("icon");
		if (icon != null)
		{
			Element span = new Element("span");
			span.setAttribute("class", "glyphicon glyphicon-" + icon);
			button.addChild(span);
			button.addChild(new Text(" "));
			for (Node node : children)
			{
				button.addChild(node);
			}
		}
		List<Node> nodes = new ArrayList<>(1);
		nodes.add(button);
		return nodes;
	}

}
