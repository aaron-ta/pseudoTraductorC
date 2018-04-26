package PseudoParser;

import Parser.Parser;
import PseudoLexer.PseudoLexer;
import SymbolTable.Symbol;
import SymbolTable.Type;
import SymbolTable.SymbolTable;
import SymbolTable.VariableSymbol;
import SymbolTable.BuiltInTypeSymbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class PseudoParser extends Parser {
	
	public boolean bool = true;
	public SymbolTable symt = new SymbolTable();
	public String varName = "";
	public VariableSymbol symbol;
	public Type entero; 
	public Type flotante; 
	public String[] varNames = new String[20];
	public int i = 0;
	
	public PseudoParser(PseudoLexer input) { 
		super(input);
		programa();
	}

	public void verificarVar() {
		if(symt.resolve(lookahead.data)==null) {
			throw new Error("Variable no declarada: " + lookahead.data);
		}
	}
	
	public void programa(){
		codigoC = codigoC + "#include<stdio.lib>\n\nvoid main(){ \n\n";
		match("INICIOPROGRAMA");
		declaraciones();
		enunciados();
		codigoC = codigoC + "}";
		System.out.println("Archivo creado?");
		try
		{
			File file = new File ("..\\CompiladorA\\src\\programa.c");
			BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
			out.write(codigoC);
			out.close();
			System.out.println("Archivo creado");
		}
		catch(Exception e) {
			System.out.println("Error "+e+"");
		}
	}
	
	public void declaraciones() {
		listavariables();
		match("DOSPUNTOS");
		System.out.println(i);
		System.out.println(lookahead.type.toString());
		
		if (lookahead.type.toString().equals("TIPO")) {
			System.out.println(i);
			System.out.println(lookahead.data.toString());
			if (lookahead.data.equals("entero")) {
				while(i>=0) {
					symbol = new VariableSymbol(varNames[i], new BuiltInTypeSymbol("entero"));
					System.out.println(symbol.type.toString());
					symt.define(symbol);
					System.out.println(symt);
					i--;
				}
			}
			else if (lookahead.data.equals("flotante")) {
				while(i>=0) {
					symbol = new VariableSymbol(varNames[i], new BuiltInTypeSymbol("flotante"));
					System.out.println(symbol.type.toString());
					symt.define(symbol);
					System.out.println(symt);
					i--;
				}
			}
		}

		match("TIPO");
		
		if(lookahead.type.toString().equals("VARIABLE")) {
			codigoC = codigoC + ""+lookahead.data;
			if(symt.resolve(lookahead.data)==null) {
				i=0;
				//System.out.println("LOL");
				declaraciones();
			}else
				asignacion();
		}		
	}	
		
	public void listavariables() {
		if(lookahead.type.toString().equals("VARIABLE")) {
			varNames[i] = lookahead.data.toString();
			match("VARIABLE");
		}
		if(lookahead.type.toString().equals("COMA")) {
			consume();
			System.out.println(i);
			i++;
			listavariables();
		}
	}
	
	public void enunciados() {
		String y = lookahead.type.toString();
		//System.out.println(y);
		//System.out.println(lookahead.data);
		while(!(lookahead.type.toString().equals("FINPROGRAMA"))){
			codigoC = codigoC + "\t";
			enunciado();
		}
		consume();
	}
	
	public void enunciado() {
		//System.out.println(lookahead.type);
		//System.out.println(lookahead.data);
		if(lookahead.type.toString().equals("VARIABLE")){
			asignacion();
			codigoC = codigoC + ";\n";
		}
		else if(lookahead.type.toString().equals("LEER")) {
			leer();
			codigoC = codigoC + ";\n";
		}
		else if(lookahead.type.toString().equals("ESCRIBIR")) {
			escribir();
			codigoC = codigoC + ";\n";
		}
		else if(lookahead.type.toString().equals("SI")) {
			si();
		}
		else if(lookahead.type.toString().equals("MIENTRAS")) {
			//System.out.println("DEBUGMI");
			mientras();
		}
		
	}
	
	public void asignacion() {
		codigoC = codigoC + ""+lookahead.data;
		verificarVar();
		match("VARIABLE");
		codigoC = codigoC + lookahead.data;
		match("IGUAL");
		//check
		operacion();
	}
	
	
	public void operacion() {
		valor();
		if(lookahead.type.toString().equals("OPARITMETICO")) {

			codigoC = codigoC + lookahead.data;
			consume();
			valor();
		}	
	}
	
	public void valor() {
			if (lookahead.type.toString().equals("NUMERO")) {
				codigoC = codigoC + lookahead.data;
				consume();
			}else if (lookahead.type.toString().equals("VARIABLE")) {
				verificarVar();
				codigoC = codigoC + lookahead.data;
				consume();
			}
	}
	
	public void leer() {
		codigoC = codigoC + "scanf(";
		match("LEER");
		codigoC = codigoC + lookahead.data;
		match("CADENA");
		codigoC = codigoC + "%d";
		codigoC = codigoC + lookahead.data;
		match("COMA");
		codigoC = codigoC + lookahead.data;
		verificarVar();
		match("VARIABLE"); 
		//System.out.println("LEER");
		codigoC = codigoC + ")";
	}
	
	public void escribir() {
		match("ESCRIBIR");
		codigoC = codigoC + "printf(";
		codigoC = codigoC + lookahead.data;
		match("CADENA");
		if(lookahead.type.toString().equals("COMA")) {
			codigoC = codigoC + lookahead.data;
			consume();
			codigoC = codigoC + lookahead.data;
			verificarVar();
			match("VARIABLE");
		}
		codigoC = codigoC + ")";
	}
	
	public void si() {
		match("SI");
		codigoC = codigoC + "\n\tif ";
		codigoC = codigoC + lookahead.data;
		match("PARENTESISIZQ");
		comparacion();
		codigoC = codigoC + lookahead.data;
		match("PARENTESISDER");
		match("ENTONCES");
		codigoC = codigoC + "\t{ \n";
		while(!(lookahead.type.toString().equals("FINSI"))){
			codigoC = codigoC + "\t\t";
			enunciado();	
		}
		codigoC = codigoC + "\t} \n";
		consume();
	}
	
	public void mientras() {
		codigoC = codigoC + "\n\twhile";
		match("MIENTRAS");
		//System.out.println("MIENTRAS");
		codigoC = codigoC + lookahead.data;
		match("PARENTESISIZQ");
		comparacion();
		codigoC = codigoC + lookahead.data;
		match("PARENTESISDER");
		codigoC = codigoC + "{\n";
		while(!(lookahead.type.toString().equals("FINMIENTRAS"))){
			codigoC = codigoC + "\t\t";
			enunciado();
		}
		codigoC = codigoC + "\t} \n";
		consume();
	}
	
	public void comparacion() {
		//System.out.println("COMP");
		valor();
		codigoC = codigoC + lookahead.data;
		match("OPERACIONAL");
		valor();
	}
	
}