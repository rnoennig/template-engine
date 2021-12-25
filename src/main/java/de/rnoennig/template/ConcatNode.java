package de.rnoennig.template;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class ConcatNode extends RenderNode {

	private List<RenderNode> children;

	public ConcatNode(List<RenderNode> children) {
		this.children = children;
	}

	@Override
	public void render(Writer writer, Map<String, Object> params) throws IOException {
		for (RenderNode child : children) {
			child.render(writer, params);
		}
	}

}
