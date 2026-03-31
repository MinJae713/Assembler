package msModel.assembler.parser;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dto.assembler.ParsingInfo;
import msConstants.ModelConsts;
import msModel.assembler.lexicalAnalyzer.EKeyword;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.segments.CodeSegment;
import msModel.assembler.node.segments.DataSegment;
import msModel.assembler.node.segments.HeaderSegment;
import msModel.assembler.node.segments.Program;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;

public class Parser {
	protected LexicalAnalyzer lexicalAnalyzer;
	protected SymbolTable symbolTable;
	private Program program;
	private HeaderSegment headerSegment;
	private DataSegment dataSegment;
	private CodeSegment codeSegment;
	private EKeyword parsingStatus;
	
	public Parser(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	public void initialize() {
		program = null;
		headerSegment = null;
		dataSegment = null;
		codeSegment = null;
		parsingStatus = EKeyword.eProgram;
	}
	public ParsingInfo parse(Vector<String> tokenVector, 
			int tokenCount) throws Exception {
		ParsingInfo parsingInfo = new ParsingInfo();
		parsingInfo.setTokenIdx(tokenCount);
		parsingInfo.setCurToken(tokenVector.get(tokenCount));
		boolean newSymbol = symbolTable.getSymbolFromName(
				tokenVector.get(tokenCount)) == null;
		Token token = this.lexicalAnalyzer.getToken(tokenVector, tokenCount);
		if (token.getSymbolType() == ESymbolType.eConstant)
			newSymbol = false; 
		if (token.getSymbolType() == ESymbolType.eComment) {
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			return parsingInfo; 
		}
		if (parsingStatus == EKeyword.eProgram) {
			if (token.getName().equals(EKeyword.eProgram.getText()))
				program = new Program(this.lexicalAnalyzer, symbolTable);
			parsingInfo = program.parse(token, parsingInfo);
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			parsingStatus = getParsingStatus(tokenVector, tokenCount+1, EKeyword.eProgram);
			return parsingInfo;
		} else if (parsingStatus == EKeyword.eHeader) {
			if (token.getName().equals(EKeyword.eHeader.getText())) 
				headerSegment = new HeaderSegment(lexicalAnalyzer, symbolTable);
			parsingInfo = headerSegment.parse(token, parsingInfo);
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			parsingStatus = getParsingStatus(tokenVector, tokenCount+1, EKeyword.eHeader);
			return parsingInfo;
		} else if (parsingStatus == EKeyword.eData) {
			if (token.getName().equals(EKeyword.eData.getText()))
				dataSegment = new DataSegment(lexicalAnalyzer, symbolTable);
			parsingInfo = dataSegment.parse(token, parsingInfo);
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			parsingStatus = getParsingStatus(tokenVector, tokenCount+1, EKeyword.eData);
			return parsingInfo;
		} else if (parsingStatus == EKeyword.eCode) {
			if (token.getName().equals(EKeyword.eCode.getText()))
				codeSegment = new CodeSegment(lexicalAnalyzer, symbolTable);
			codeSegment.setParsingStatus(tokenVector.get(tokenCount+1));
			parsingInfo = codeSegment.parse(token, parsingInfo);
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			parsingStatus = getParsingStatus(tokenVector, tokenCount+1, EKeyword.eCode);
			return parsingInfo;
		} else if (parsingStatus == EKeyword.eEnd) {
			if (token.getName().equals(EKeyword.eEnd.getText())) {
				symbolLabelSetting(); 
				if (program == null) 
					throw new Exception(ModelConsts.noProgramException);
				else if (headerSegment == null) 
					throw new Exception(ModelConsts.noHSException);
				else if (dataSegment == null) 
					throw new Exception(ModelConsts.noDSException);
				else if (codeSegment == null) 
					throw new Exception(ModelConsts.noCSException);
				program.setHeaderSegment(headerSegment);
				program.setDataSegment(dataSegment);
				program.setCodeSegment(codeSegment);
			}
			parsingInfo.setRemoveIdentifier(true);
			parsingInfo.setNewSymbol(newSymbol);
			if (newSymbol) parsingInfo.setSymbol(
					symbolTable.getSymbolTable().lastElement());
			return parsingInfo;
		}
		throw new Exception();
	}
	private void symbolLabelSetting() {
		List<Symbol> labelSymbols = new ArrayList<Symbol>();
		Vector<Symbol> symbols = symbolTable.getSymbolTable();
		for (int i=0; i<symbols.size(); i++) {
			Symbol symbol = symbols.get(i);
			if (symbol.getSymbolType() == ESymbolType.eLabel) {
				String labelName = symbol.getName();
				labelName = labelName.substring(0, labelName.length()-1);
				symbol.setName(labelName);
				symbols.set(i, symbol);
				labelSymbols.add(symbol);
			}
		} 
		for (int i=0; i<symbols.size(); i++) {
			Symbol symbol = symbols.get(i);
			if (symbol.getSymbolType() == ESymbolType.eIdentifier
					&& isContainLabel(symbol, labelSymbols)) 
				symbols.remove(i);
		}
		symbolTable.setSymbolTable(symbols);
	}
	private boolean isContainLabel(Symbol symbol, List<Symbol> labelSymbols) {
		for (Symbol labelSymbol : labelSymbols)
			if (labelSymbol.getName().equals(symbol.getName()))
				return true;
		return false;
	}
	private EKeyword getParsingStatus(Vector<String> tokenVector, 
			int tokenCount, EKeyword curKeyword) {
		EKeyword nextKeyword = null;
		if (curKeyword == EKeyword.eProgram) nextKeyword = EKeyword.eHeader;
		else if (curKeyword == EKeyword.eHeader) nextKeyword = EKeyword.eData;
		else if (curKeyword == EKeyword.eData) nextKeyword = EKeyword.eCode;
		else if (curKeyword == EKeyword.eCode) nextKeyword = EKeyword.eEnd;
		return commentCheck(tokenVector, tokenCount).equals(
				nextKeyword.getText()) ? nextKeyword : curKeyword;
	}
	private String commentCheck(Vector<String> tokenVector, int tokenCount) {
		int count = tokenCount;
		String result = tokenVector.get(count);
		while (result.equals("nop")) {
			count++;
			result = tokenVector.get(count);
		}
		return result;
	}
	public Program getProgram() {
		return program;
	}
	@Override
	public String toString() {
		return program.toString();
	}
	public void finish() {}
	public void associate(LexicalAnalyzer lexicalAnalyzer) {
		this.lexicalAnalyzer = lexicalAnalyzer;
	}
}
