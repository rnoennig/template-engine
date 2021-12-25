package de.rnoennig.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class TextNode extends RenderNode {

	private String value;

	public TextNode(String value) {
		this.value = value;
	}

	@Override
	public void render(Writer writer, Map<String, Object> params) throws IOException {
		writer.write(value);
	}

}
