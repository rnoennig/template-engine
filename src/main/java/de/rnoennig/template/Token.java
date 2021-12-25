package de.rnoennig.template;

public class Token {
	private Type type;
	private Object value;

	public Token(Type tokenType, Object value) {
		this.type = tokenType;
		this.value = value;
	}

	public enum Type {
		EOF, TEXT, IF, ELSE, END
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", value=" + value + "]";
	}

	public Type getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
	
}
