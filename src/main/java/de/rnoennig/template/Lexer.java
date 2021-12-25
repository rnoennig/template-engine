package de.rnoennig.template;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.rnoennig.template.Token.Type;

public class Lexer {

	private List<Token> tokens;
	
	private String source;
	
	private Pattern ifBlockPattern = Pattern.compile("(?<if>%if (?<patternname>[^%]+)%)");
	private Pattern elseBlockPattern = Pattern.compile("(?<else>%else%)");
	private Pattern endBlockPattern = Pattern.compile("(?<end>%end%)");
	private Pattern blockPattern = Pattern.compile(ifBlockPattern+"|"+elseBlockPattern+"|"+endBlockPattern);
	private int index;

	public List<Token> tokenize(Reader templateReader) {
		this.source = new BufferedReader(templateReader).lines().collect(Collectors.joining("\n"));
		this.index = 0;
		this.tokens = new ArrayList<>();
	    
	    while (index < source.length() - 1) {
	    	tokenizeText();
	    }
	    
	    this.addToken(Token.Type.EOF);
	    
		return this.tokens;
	}

	private void tokenizeText() {
	    Matcher matcher = blockPattern.matcher(this.source.substring(index, this.source.length()));
	    boolean match = matcher.find();

	    String text;
	    if (!match) {
	      text = this.source.substring(index, this.source.length());
	    } else {
	      text = this.source.substring(index, index+matcher.start());
	    }
	    this.addToken(Token.Type.TEXT, text);
	    index += text.length();
	    
	    if (match) {
	    	if (matcher.group("if") != null) {
	    		addToken(Type.IF, matcher.group("patternname"));
	    	} else if (matcher.group("else") != null) {
	    		addToken(Type.ELSE);
	    	} else if (matcher.group("end") != null) {
	    		addToken(Type.END);
	    	} else {
	    		throw new IllegalStateException("blockPattern contains unhandled token type");
	    	}
	    	index += matcher.group().length();
	    }
	}

	private void addToken(Type tokenType, Object value) {
		if (tokenType.equals(Token.Type.TEXT) && (value == null || value.toString().isEmpty())) {
			// skip empty text tokens, e.g. when template immediately begins with %if pattern%
			return;
		}
		Token token = new Token(tokenType, value);
		this.tokens.add(token);
	}

	private void addToken(Type tokenType) {
		addToken(tokenType, null);
	}

}
