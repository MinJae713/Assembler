package msModel.assembler.lexicalAnalyzer;
import java.util.Vector;

import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;

public class LexicalAnalyzer {
	
	private SymbolTable symbolTable;
	public LexicalAnalyzer(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	public void initialize() {}
	public void finalize() {}
	public Token getToken(Vector<String> tokenVector, int tokenCount) {
		if (tokenCount < tokenVector.size()) {
			String token = tokenVector.get(tokenCount);
			if (token.matches("[0-9]+")) 
				return new Token(token, ESymbolType.eConstant);
			Symbol curSymbol = symbolTable.getSymbolFromName(token);
			if (curSymbol == null) {
				Symbol symbol = createNewSymbol(token);
				if (symbol != null) symbolTable.add(symbol);
				return symbol.getToken();
			}
			return curSymbol.getToken();
		}
		return null;
	}
	private Symbol createNewSymbol(String token) {
		Symbol symbol = null;
		if (isSegmentHead(token)) 
			symbol = new Symbol(token, ESymbolType.eSegmentHead);
		else if (isNop(token))
			symbol = new Symbol(token, ESymbolType.eComment);
		else if (isLabel(token))
			symbol = new Symbol(token, ESymbolType.eLabel);
		else if (isRegister(token))
			symbol = new Symbol(token, ESymbolType.eRegister);
		else symbol = new Symbol(token, ESymbolType.eIdentifier);
		return symbol;
	}
	private boolean isSegmentHead(String token) {
		return token.charAt(0) == '.';
	}
	private boolean isNop(String token) {
		return token.equals("nop");
	}
	private boolean isLabel(String token) {
		return token.charAt(token.length()-1) == ':';
	}
	private boolean isRegister(String token) {
		String tokenSub = token.substring(1);
		return token.charAt(0) == 'r' && tokenSub.matches("[0-9]+");
	}
	public void finish() {}
}
