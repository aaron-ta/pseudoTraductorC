import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {
	//private static final String FILENAME = "texto.txt";

	public static void main(String[] args) throws IOException {
		String content = new Scanner(new File("..\\CompiladorAARON\\src\\texto.txt")).useDelimiter("\\Z").next();
		//System.out.println(content);
		PseudoLexer pseuLexer = new PseudoLexer(content);
		PseudoParser parser = new PseudoParser(pseuLexer); // create parser
	}
}
