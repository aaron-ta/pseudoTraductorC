import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import PseudoLexer.PseudoLexer;
import PseudoParser.PseudoParser;

public class Main {
	//private static final String FILENAME = "texto.txt";

	public static void main(String[] args) throws IOException {
		int i;
		String content = new Scanner(new File("..\\CompiladorA\\src\\texto.txt")).useDelimiter("\\Z").next();
		//System.out.println(content);
		PseudoLexer pseuLexer = new PseudoLexer(content);
		PseudoParser parser = new PseudoParser(pseuLexer); // create parser
	}

}
