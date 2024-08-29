package varlang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Lexer;
import varlang.AST.Program;
import varlang.parser.VarLangLexer;
import varlang.parser.VarLangParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {

    @SuppressWarnings("SameReturnValue")
    protected String getProgramDirectory() {
        return "src/main/java/varlang/examples/";
    }

    public Program read() throws IOException {
        String programText = readNextProgram();
        return parse(programText);
    }

    public Program parse(String programText) {
        Lexer l = getLexer(CharStreams.fromString(programText));
        VarLangParser p = getParser(new org.antlr.v4.runtime.CommonTokenStream(l));
        return p.program().ast;
    }

    protected Lexer getLexer(CharStream s) {
        return new VarLangLexer(s);
    }

    protected VarLangParser getParser(org.antlr.v4.runtime.CommonTokenStream s) {
        return new VarLangParser(s);
    }

    protected String readNextProgram() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("$ ");
        String programText = br.readLine();
        return runFile(programText);
    }

    protected String runFile(String programText) throws IOException {
        if (programText.startsWith("run ")) {
            programText = readFile(getProgramDirectory() + programText.substring(4));
        }
        return programText;
    }

    private String readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }
}
