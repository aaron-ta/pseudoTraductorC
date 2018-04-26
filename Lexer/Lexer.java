package Lexer;

import PseudoLexer.PseudoLexer.Token;

public abstract class Lexer {
	protected static final char EOF = (char)-1;
	protected static final int EOF_TYPE = 1;
	String input; // input string
	int index = 0; // index into input of current char
	protected char c; // current char
	
	//	Constructor
	public Lexer(String d) {
		this.input = d;
		c = input.charAt(index);
	}
	//	Move one character; detect 'end of file'
	public void consume() {
		index++;
		if(index >= input.length())
			c = EOF;
		else
			c = input.charAt(index);
	}
	//	Ensure x is next character on the input stream
	public void match(char x) {
		if(c == x)
			consume();
		else 
			throw new Error("Expecting: " + x + "; Found: " + c);
	}
	//public abstract PseudoLexer.PseudoLexer.Token nextToken();
	//public abstract String getTokenName(int tokenType);
}
