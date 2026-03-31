package msModel.assembler.symbolTable;

import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.entity.DataAlloc;

public class Symbol {
	private String name;
	private ESymbolType symbolType;
	private int size;
	private int offset;
	public Symbol(String name, ESymbolType symbolType) {
		this.name = name;
		this.symbolType = symbolType;
	}
	public Symbol(String name, ESymbolType type, 
			int size, int offset) {
		this.name = name;
		this.symbolType = type;
		this.size = size;
		this.offset = offset;
	}
	public String getName() {return name;}
	public ESymbolType getSymbolType() {return symbolType;}
	public int getSize() {return size;}
	public int getOffset() {return offset;}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setSymbolType(ESymbolType type) {
		this.symbolType = type;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public DataAlloc getDataAlloc() {
		return new DataAlloc(name, size, offset);
	}
	public Token getToken() {
		return new Token(name, symbolType);
	}
	@Override
	public boolean equals(Object obj) {
		Symbol symbol = (Symbol)obj;
		return this.name.equals(symbol.getName());
	}
	@Override
	public String toString() {
		return name+", "+symbolType+", "+size+", "+offset;
	}
}
