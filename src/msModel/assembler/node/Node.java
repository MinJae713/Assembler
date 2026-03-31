package msModel.assembler.node;
import java.util.List;

import dto.assembler.ParsingInfo;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;

public abstract class Node {
	protected LexicalAnalyzer lexicalAnalyzer;
	protected SymbolTable symbolTable;
	public Node(SymbolTable symbolTable) {
		this.lexicalAnalyzer = null;
		this.symbolTable = symbolTable;
	}
	public Node(LexicalAnalyzer lexicalAnalyzer, SymbolTable symbolTable) {
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.symbolTable = symbolTable;
	}
	protected int getSymbolIdx(Symbol symbol) {
		List<Symbol> symbols = symbolTable.getSymbolTable();
		for (int i=0; i<symbols.size(); i++)
			if (symbols.get(i).equals(symbol)) return i;
		return -1;
	}
	public abstract ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception;
}
