
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class PseudoParser extends Parser {
	public PseudoParser(PseudoLexer input) { 
		super(input); 
		programa();
	}

	public void programa(){
		//un programa se compone de un token "INICIOPROGRAMA" seguido de 0 o más enunciados, y un token FINPROGRAMA al final
		match("INICIOPROGRAMA");
		enunciados();
		System.out.println("Archivo creado?");
		try
		{
			  File file = new File ("..\\CompiladorAARON\\src\\programa.c");
			  BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
			  out.write(codigoC);
			  out.close();
			  System.out.println("Archivo creado");
		}
		catch(Exception e) {
			
		}
	}
	
	public void enunciados() {
		String y = lookahead.type.toString();
		System.out.println(y);
		System.out.println(lookahead.data);
		while(!(lookahead.type.toString().equals("FINPROGRAMA"))){
			enunciado();
		}
		consume();
	}

	public void enunciado() {
		System.out.println(lookahead.type);
		System.out.println(lookahead.data);
		if(lookahead.type.toString().equals("VARIABLE"))
			asignacion();
		else if(lookahead.type.toString().equals("LEER"))
			leer();
		else if(lookahead.type.toString().equals("ESCRIBIR"))
			escribir();
		else if(lookahead.type.toString().equals("SI"))
			si();
		else if(lookahead.type.toString().equals("MIENTRAS")) {
			System.out.println("DEBUGMI");
			mientras();
		}
		//System.out.println("DEBUG");
	}
	
	public void asignacion() {
		match("VARIABLE");
		match("IGUAL");
		operacion();
		System.out.println("DEBUGAS");
	}
	
	
	public void operacion() {
		valor();
		System.out.println("DEBUGOP");
		if(lookahead.type.toString().equals("OPARITMETICO")) {
			System.out.println("DEBUGOPOP");
			consume();
			valor();
		}	
	}
	
	public void valor() {
			if (lookahead.type.toString().equals("NUMERO")||lookahead.type.toString().equals("VARIABLE")){
				consume();
			}
	}
	
	public void leer() {
		match("LEER");
		match("CADENA");
		codigoC = codigoC + "%d";
		match("COMA");
		match("VARIABLE"); 
		//System.out.println("LEER");
		codigoC = codigoC + ")\n";
	}
	
	public void escribir() {
		match("ESCRIBIR");
		match("CADENA");
		if(lookahead.type.toString().equals("COMA")) {
			consume();
			match("VARIABLE");
		}
	}
	
	public void si() {
		match("SI");
		match("PARENTESISIZQ");
		comparacion();
		match("PARENTESISDER");
		match("ENTONCES");
		while(!(lookahead.type.toString().equals("FINSI"))){
			enunciado();
		}
		consume();
	}
	
	public void mientras() {
		match("MIENTRAS");
		System.out.println("MIENTRAS");
		match("PARENTESISIZQ");
		comparacion();
		match("PARENTESISDER");
		codigoC = codigoC + "{";
		while(!(lookahead.type.toString().equals("FINMIENTRAS"))){
			enunciado();
		}
		consume();
	}
	
	public void comparacion() {
		System.out.println("COMP");
		valor();
		match("OPERACIONAL");
		valor();
	}
	

}