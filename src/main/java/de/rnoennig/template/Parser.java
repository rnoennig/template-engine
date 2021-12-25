package de.rnoennig.template;

import java.util.ArrayList;
import java.util.List;

import de.rnoennig.template.Token.Type;

/**
 * Responsible for turning tokens into a syntax tree
 */
public class Parser {
	
	int index = 0;
	private List<Token> tokens;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	public RenderNode parse() {
		return template();
	}

	private void consume(Type tokenType) {
		if (this.tokens.get(index).getType() == tokenType) {
			index++;
		} else {
			throw new IllegalStateException("Invalid Syntax");
		}
	}

	/**
	 * <template> ::= (<ifelse> | <text>)+
	 * @param else1 
	 */
	private RenderNode template(Type... stopTokenType) {
		List<RenderNode> nodes = new ArrayList<>();
		boolean isIfTokenNext;
		boolean isTextTokenNext;
		boolean isStopTokenNext = false;
		do {
			Token token = this.tokens.get(index);
			if (token.getType() == Type.EOF) {
				break;
			}
			isIfTokenNext = token.getType().equals(Type.IF);
			isTextTokenNext = token.getType().equals(Type.TEXT);
			if (stopTokenType.length > 0) {
				isStopTokenNext = token.getType().equals(stopTokenType[0]);
			}
			
			if (isIfTokenNext) {
				nodes.add(this.ifelse());
			} else if (isTextTokenNext) {
				nodes.add(this.text());
			} else if (isStopTokenNext) {
				break;
			} else {
				throw new IllegalStateException("expected if or text token, but got " + token);
			}
		} while (isIfTokenNext || isTextTokenNext);
		if (nodes.isEmpty()) {
			throw new IllegalStateException("template is empty");
		} else if (nodes.size() == 1) {
			return nodes.get(0);
		}
		return new ConcatNode(nodes);
	}
	
	/**
	 * <ifelse> ::= "%if " <patternname> "%" <template> "%else%" <template> "%end%"
	 */
	private RenderNode ifelse() {
		Token ifToken = this.tokens.get(index);
    	this.consume(Type.IF);
    	RenderNode ifNode = this.template(Type.ELSE);
    	this.consume(Type.ELSE);
    	RenderNode elseNode = this.template(Type.END);
    	this.consume(Type.END);
        return new IfElseNode(String.valueOf(ifToken.getValue()), ifNode, elseNode);
	}
	
	/**
	 * <text> ::= (<digit> | <lowercaseletter> | <uppercaseletter> | <special> | <whitespace>)+
	 */
	private TextNode text() {
		Token token = this.tokens.get(index);
    	this.consume(Type.TEXT);
        return new TextNode(String.valueOf(token.getValue()));
	}

}
