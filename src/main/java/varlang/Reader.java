package varlang;

import org.antlr.v4.runtime.CharStreams;
import varlang.AST.Program;
import varlang.parser.VarLangLexer;
import varlang.parser.VarLangParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
    Program read() throws IOException {
        String programText = readNextProgram();
        VarLangLexer l = new VarLangLexer(CharStreams.fromString(programText));
        VarLangParser p = new VarLangParser(new org.antlr.v4.runtime.CommonTokenStream(l));
        return p.program().ast;
    }

    private String readNextProgram() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("$ ");
        return br.readLine();
    }
}
