package msModel.assembler.lexicalAnalyzer;

import msModel.assembler.symbolTable.ESymbolType;

public class Token {
	private String name;
	private ESymbolType symbolType;
	public Token(String name, ESymbolType symbolType) {
		this.name = name;
		this.symbolType = symbolType;
	}
	public String getName() {
		return name;
	}
	public ESymbolType getSymbolType() {
		return symbolType;
	}
	@Override
	public String toString() {
		return "Token - name:"+name+", type:"+symbolType;
	}
}
