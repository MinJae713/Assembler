package msModel.assembler.node.segments;
import dto.assembler.ParsingInfo;
import msConstants.ModelConsts;
import msModel.assembler.lexicalAnalyzer.EKeyword;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.Node;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;
import status.EParsingStatus;

public class HeaderSegment extends Node {

	private int sizeStack;
	private int sizeHeap;
	private boolean stackReady;
	private boolean heapReady;
	
	public HeaderSegment(LexicalAnalyzer lexicalAnalyzer, SymbolTable symbolTable) {
		super(lexicalAnalyzer, symbolTable);
		stackReady = false;
		heapReady = false;
	}

	@Override
	public ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception {
		if (token.getSymbolType() == ESymbolType.eSegmentHead) 
			return parsingInfo;
		String keywordName = token.getName();
		if (keywordName.equals(EKeyword.eStack.getText())) {
			symbolTable.setSymbolType(keywordName, ESymbolType.eMemoryAlloc);
			stackReady = true;
			heapReady = false;
		} else if (keywordName.equals(EKeyword.eHeap.getText())) {
			symbolTable.setSymbolType(keywordName, ESymbolType.eMemoryAlloc);
			stackReady = false;
			heapReady = true;
		} else if (token.getSymbolType() == ESymbolType.eConstant) {
			if (stackReady) {
				sizeStack = Integer.parseInt(token.getName());
				Symbol symbol = symbolTable.setSymbolSize(EKeyword.eStack.getText(), sizeStack);
				if (symbol == null) throw new IllegalArgumentException(
						ModelConsts.symbolNotFoundException);
				int idx = getSymbolIdx(symbol);
				parsingInfo.setSymbol(symbol);
				parsingInfo.setUpdateSymbolIdx(idx);
				parsingInfo.setStatus(EParsingStatus.eStackSize); 
				parsingInfo.setStackSize(sizeStack);
			} else if (heapReady) {
				sizeHeap = Integer.parseInt(token.getName());
				Symbol symbol = symbolTable.setSymbolSize(EKeyword.eHeap.getText(), sizeHeap);
				if (symbol == null) throw new IllegalArgumentException(
						ModelConsts.symbolNotFoundException);
				int idx = getSymbolIdx(symbol);
				parsingInfo.setSymbol(symbol);
				parsingInfo.setUpdateSymbolIdx(idx);
				parsingInfo.setStatus(EParsingStatus.eHeapSize);
				parsingInfo.setHeapSize(sizeHeap);
			}
			stackReady = false;
			heapReady = false;
		} else throw new Exception();
		return parsingInfo;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("\n[Header Segment]\n");
		buffer.append("stack:"+sizeStack+"\n");
		buffer.append("heap:"+sizeHeap+"\n");
		return buffer.toString();
	}
	public int getSizeStack() {
		return sizeStack;
	}
	public int getSizeHeap() {
		return sizeHeap;
	}

	
}
