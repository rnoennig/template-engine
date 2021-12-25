package de.rnoennig.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class Template {

	private RenderNode root;

	public Template(RenderNode root) {
		this.root = root;
	}

	public void render(Writer writer, Map<String, Object> params) throws IOException {
		this.root.render(writer, params);
	}

}
