package msModel.assembler.symbolTable;
import java.util.Vector;

public class SymbolTable {
	private Vector<Symbol> symbolTable;
	public void initialize() {
		symbolTable = new Vector<Symbol>();
	}
	public void finish() {}
	public boolean add(Symbol symbol) {
		return symbolTable.add(symbol);
	}
	public Symbol setSymbolAllInfo(String name, ESymbolType 
			symbolType, int size, int offset) {
		Symbol curSymbol = getSymbolFromName(name);
		curSymbol.setSymbolType(symbolType);
		curSymbol.setSize(size);
		curSymbol.setOffset(offset);
		return updateSymbolTable(curSymbol);
	}
	public Symbol setSymbolType(String name, ESymbolType symbolType) {
		Symbol curSymbol = getSymbolFromName(name);
		curSymbol.setSymbolType(symbolType);
		return updateSymbolTable(curSymbol);
	}
	public Symbol setSymbolSize(String name, int size) {
		Symbol curSymbol = getSymbolFromName(name);
		curSymbol.setSize(size);
		return updateSymbolTable(curSymbol);
	}
	public Symbol setSymbolOffset(String name, int offset) {
		Symbol curSymbol = getSymbolFromName(name);
		curSymbol.setOffset(offset);
		return updateSymbolTable(curSymbol);
	}
	public int getSymbolIdxFromName(String name) {
		if (name == null) return -1;
		for (int i=0; i<symbolTable.size(); i++)
			if (symbolTable.get(i).getName().equals(name))
				return i;
		return -1;
	}
	public Symbol getSymbolFromName(String name) {
		for (Symbol symbol : symbolTable)
			if (symbol.getName().equals(name))
				return symbol;
		return null;
	}
	private Symbol updateSymbolTable(Symbol curSymbol) {
		for (int i = 0; i < symbolTable.size(); i++) 
			if (symbolTable.get(i).equals(curSymbol)) {
				symbolTable.set(i, curSymbol);
				return symbolTable.get(i);
			}
		return null;
	}
	public Vector<Symbol> getSymbolTable() {
		return symbolTable;
	}
	public void setSymbolTable(Vector<Symbol> symbolTable) {
		this.symbolTable = symbolTable;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[Symbols]\n");
		for (Symbol symbol : symbolTable)
			buffer.append(symbol+"\n");
		return buffer.toString();
	}
}
