package de.rnoennig.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TemplateTest {

	private TemplateEngine engine;

	@BeforeEach
	void setUp() throws Exception {
		engine = new TemplateEngine();
	}

	@Test
	void shouldRenderIfBranchWhenPatternIsSet() throws IOException {
		Template singlePattern = engine.getTemplateFromString("%if var%true%else%false%end%");
		
		Map<String, Object> params = new HashMap<>();
	    params.put("var", true);
	    
	    Writer writer = new StringWriter();
	    singlePattern.render(writer, params);
	    assertEquals("true", writer.toString());
	}
	
	@Test
	void shouldRenderElseBranchWhenPatternIsMissing() throws IOException {
		Template singlePattern = engine.getTemplateFromString("%if var%true%else%false%end%");
		
		Map<String, Object> params = new HashMap<>();
		
		Writer writer = new StringWriter();
		singlePattern.render(writer, params);
		assertEquals("false", writer.toString());
	}
	
	@Test
	void shouldRenderFile() throws IOException {
		Template singlePattern = engine.getTemplateFromFile("/templates/if-else-pattern.html");
		
		Map<String, Object> params = new HashMap<>();
		
		Writer writer = new StringWriter();
		singlePattern.render(writer, params);
		assertEquals("before if\n"
				+ "\n"
				+ "	else\n"
				+ "\n"
				+ "after if", writer.toString());
	}
	@Test
	void shouldRenderNestedIfElseBranches() throws IOException {
		Template singlePattern = engine.getTemplateFromFile("/templates/nested-if-else-pattern.html");
		
		Map<String, Object> params = new HashMap<>();
		params.put("if-pattern", true);
		params.put("if-if-pattern", true);
		
		Writer writer = new StringWriter();
		singlePattern.render(writer, params);
		assertEquals("before if\n"
				+ "\n"
				+ "	before if-if\n"
				+ "	\n"
				+ "		if-if\n"
				+ "	\n"
				+ "	after if-if\n"
				+ "\n"
				+ "after if", writer.toString());
	}
	
	@Test
	void failWhenIfTagIsMissing() throws IOException {
		assertThrows(IllegalStateException.class, () ->
		engine.getTemplateFromString("true%else%false"));
	}
	
	@Test
	/**
	 * TODO %else% delimiter is currently mandatory, might change in the future
	 * @throws IOException
	 */
	void failWhenElseTagIsMissing() throws IOException {
		assertThrows(IllegalStateException.class, () ->
		engine.getTemplateFromString("%if var%true%end%"));
	}
	
	@Test
	void failWhenEndTagIsMissing() throws IOException {
		assertThrows(IllegalStateException.class, () ->
		engine.getTemplateFromString("%if var%true%else%false"));
	}

}
