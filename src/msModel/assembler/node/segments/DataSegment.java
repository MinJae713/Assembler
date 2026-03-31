package msModel.assembler.node.segments;
import java.util.Vector;

import dto.assembler.ParsingInfo;
import msConstants.ModelConsts;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.Node;
import msModel.assembler.node.entity.DataAlloc;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;
import status.EParsingStatus;

public class DataSegment extends Node {
	private int curOffset;
	private Vector<DataAlloc> dataSegment;
	private boolean dataReady;
	private String curVariable;
	public DataSegment(LexicalAnalyzer lexicalAnalyzer, SymbolTable symbolTable) {
		super(lexicalAnalyzer, symbolTable);
		dataSegment = new Vector<DataAlloc>();
		curOffset = 0;
		initSegment();
	}
	private void initSegment() {
		dataReady = false;
		curVariable = null;
	}
	@Override
	public ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception {
		if (token.getSymbolType() == ESymbolType.eSegmentHead) 
			return parsingInfo;
		if (token.getSymbolType() == ESymbolType.eConstant && dataReady) {
			int size = Integer.parseInt(token.getName());
			Symbol symbol = symbolTable.setSymbolAllInfo(
					curVariable, ESymbolType.eData, size, curOffset); 
			if (symbol == null) throw new IllegalArgumentException(
					ModelConsts.symbolNotFoundException);
			int idx = getSymbolIdx(symbol);
			parsingInfo.setSymbol(symbol);
			parsingInfo.setUpdateSymbolIdx(idx);
			DataAlloc curDataAlloc = symbol.getDataAlloc();
			parsingInfo.setDataAlloc(curDataAlloc);
			parsingInfo.setStatus(EParsingStatus.eData);
			dataSegment.add(curDataAlloc);
			curOffset += size;
			initSegment();
		} else if (token.getSymbolType() == ESymbolType.eIdentifier && !dataReady) {
			dataReady = true;
			curVariable = token.getName();
		}
		return parsingInfo;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("\n[Data Segment]\n");
		for (Symbol symbol : symbolTable.getSymbolTable())
			if (symbol.getSymbolType() == ESymbolType.eData)
				buffer.append(symbol.getName()+" => size:"+
						symbol.getSize()+", offset:"+symbol.getOffset()+"\n");
		return buffer.toString();
	}
	public Vector<DataAlloc> getDataSegment() {
		return dataSegment;
	}
}
