
public class Parser {
	public Lexer input;
	PseudoLexer pseudoInput;
	public PseudoLexer.Token lookahead;
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
		System.out.println(lookahead.data);
		System.out.println(lookahead.type);
		
		if(y.equals(x)) {
			
			codigoC = codigoC + lookahead.data;
			
			switch (lookahead.type) {
				case SI:	codigoC = codigoC + "\tif (";
							break;
				case INICIOPROGRAMA: codigoC = codigoC + "void main(){ \n";
							break;
				case FINPROGRAMA:	codigoC = codigoC + "}";
							break;
				case ENTONCES:	codigoC = codigoC + "\t{ \n";
							break;
				case FINSI:	codigoC = codigoC + "\t} \n";
							break;
				case MIENTRAS:	codigoC = codigoC + "\twhile \n";
							break;
				case FINMIENTRAS:	codigoC = codigoC + "\t} \n";
							break;
				case LEER:	codigoC = codigoC + "\tscanf(";
							break;	
				case ESCRIBIR:	codigoC = codigoC + "\tprintf(";
							break;				
				default:	codigoC = codigoC + lookahead.data;
							break;
			}
			consume();
		}
		else throw new Error("Expecting");
	}
	public void consume() { lookahead = pseudoInput.nextToken(); }
}
