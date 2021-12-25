package de.rnoennig.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public abstract class RenderNode {

	public abstract void render(Writer writer, Map<String, Object> params) throws IOException;

}
