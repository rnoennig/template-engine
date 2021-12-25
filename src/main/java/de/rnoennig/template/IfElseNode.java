package de.rnoennig.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class IfElseNode extends RenderNode {

	private RenderNode ifNode;
	private RenderNode elseNode;
	private String patternName;

	public IfElseNode(String patternName, RenderNode ifNode, RenderNode elseNode) {
		this.patternName = patternName;
		this.ifNode = ifNode;
		this.elseNode = elseNode;
	}

	@Override
	public void render(Writer writer, Map<String, Object> params) throws IOException {
		if (params.containsKey(patternName)) {
			ifNode.render(writer, params);
		} else {
			elseNode.render(writer, params);
		}
	}

}
