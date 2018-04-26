package PseudoLexer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Lexer.Lexer;
import PseudoLexer.PseudoLexer.TokenType;

public class PseudoLexer extends Lexer{
	public ArrayList<Token> tokens = new ArrayList<Token>();
	public int i = 0;
	
	public enum TokenType {
		NUMERO("-?[0-9]+(\\.([0-9]+))?"),
		CADENA("\".*\""),
		OPARITMETICO("[*|/|+|-]"),
		OPERACIONAL("<|>|==|<=|>=|!="),
		IGUAL("="),
		INICIOPROGRAMA("inicio-programa"),
		FINPROGRAMA("fin-programa"),
		SI("si"),
		ENTONCES("entonces"),
		FINSI("fin-si"),
		MIENTRAS("mientras"),
		FINMIENTRAS("fin-mientras"),
		LEER("leer"),
		ESCRIBIR("escribir"),
		COMA(","),
		PARENTESISIZQ("\\("),
		PARENTESISDER("\\)"),
		ESPACIOS("[ \t\f\r\n]+"),
		TIPO("entero|flotante"),
		VARIABLE("[a-zA-Z][a-zA-Z0-9]*"),
		DOSPUNTOS(":"),		
		ERROR(".+");
		
		public final String pattern;
		
		private TokenType(String pattern) {
			this.pattern = pattern;
		}
	}

	
	
	public class Token {
		public TokenType type;
		public String data;
		//	Constructor
		public Token(TokenType type, String data) {
			this.type = type;
			this.data = data;
		}
		// Overriding toString function
		@Override
		public String toString() {
			return String.format("(%s \"%s\")", type.name(), data);
		}
	}
	
	
	
	
	public ArrayList<Token> lex(String input) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		StringBuffer tokenPatternsBuffer = new StringBuffer();
		
		for(TokenType tokenType : TokenType.values())
			tokenPatternsBuffer.append(String.format("|(?<%s>%s))", tokenType.name(), tokenType.pattern));
	
		Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1))); //	Se elimina primer separador de la cadena ya que no es necesario.
		
		Matcher matcher = tokenPatterns.matcher(input);
	
		while(matcher.find()) {
			for(TokenType t: TokenType.values()) {
				if(matcher.group(TokenType.ESPACIOS.name()) != null)
					continue;
				else if(matcher.group(t.name()) != null) {
					tokens.add(new Token(t, matcher.group(t.name())));
					break;
				}
			}
		}
		
		return tokens;
	}

	public PseudoLexer(String input) {
		super(input); 
		StringBuffer tokenPatternsBuffer = new StringBuffer();
		for(TokenType tokenType : TokenType.values())
			tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));

		Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

		Matcher matcher = tokenPatterns.matcher(input);
		while(matcher.find()) {
			for(TokenType t: TokenType.values()) {
				if(matcher.group(TokenType.ESPACIOS.name()) != null)
					continue;
				else if(matcher.group(t.name()) != null) {
					//tokens.add(new Token(t, matcher.group(t.name())!= null) {
					  tokens.add(new Token(t,matcher.group(t.name())));
					break;	
				}
			}
		}
	}

	//////////////////////////////////
	
	public Token nextToken() {
		//System.out.println(i);
		int limite = tokens.size();
		
		while (i<limite) {
				i++;
				return tokens.get(i-1);
			}
		return new PseudoLexer.Token(TokenType.ERROR, "+");
	}
	
	//checks for whitespace or special characters
	public void WS() {
		while(c == ' ' || c == '\t' || c == '\n' || c == '\r') 
			consume();
	}
	
	/////////////////////////////////////
	
}
