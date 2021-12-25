package de.rnoennig.template;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class TemplateEngine {

	public Template getTemplate(InputStream resourceAsStream) throws FileNotFoundException {
		Reader templateReader = new InputStreamReader(resourceAsStream);
		Lexer lexer = new Lexer();
		List<Token> tokens = lexer.tokenize(templateReader);
		Parser parser = new Parser(tokens);
		RenderNode root = parser.parse();
		return new Template(root);
	}
	
	public Template getTemplateFromFile(String templatePath) throws FileNotFoundException {
		// TODO implement caching here
		InputStream resourceAsStream = TemplateEngine.class.getResourceAsStream(templatePath);
		return getTemplate(resourceAsStream);
	}

	public Template getTemplateFromString(String template) throws FileNotFoundException {
		InputStream resourceAsStream = new ByteArrayInputStream(template.getBytes(StandardCharsets.UTF_8));
		return getTemplate(resourceAsStream);
	}

}
