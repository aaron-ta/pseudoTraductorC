package Parser;
import Lexer.Lexer;
import PseudoLexer.PseudoLexer;
import PseudoLexer.PseudoLexer.Token;

public class Parser {
	public Lexer input;
	PseudoLexer pseudoInput;
	public Token lookahead;
	public String codigoC = "";
	public Parser(Lexer in) 
	{
		input = in;
		consume();
	}
	
	public Parser(PseudoLexer in)
	{
		pseudoInput = in;
		consume();
	}

	public void match(String x) {
		
		String y = lookahead.type.toString();
		//System.out.println(lookahead.data);
		//System.out.println(lookahead.type);
		
		if(y.equals(x)) {
			consume();
		}
		else throw new Error("Expecting: " + x);
	}
	public void consume() { lookahead = pseudoInput.nextToken(); }
}
