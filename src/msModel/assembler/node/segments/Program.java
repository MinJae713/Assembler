package msModel.assembler.node.segments;
import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import msModel.assembler.CodeGetter;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.Node;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.SymbolTable;

public class Program extends Node implements CodeGetter{
	
	private String name;
	private HeaderSegment headerSegment;
	private DataSegment dataSegment;
	private CodeSegment codeSegment;

	public Program(LexicalAnalyzer lexicalAnalyzer, SymbolTable symbolTable) {
		super(lexicalAnalyzer, symbolTable);
	}

	@Override
	public ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception {
		if (token.getSymbolType() == ESymbolType.eSegmentHead) 
			return parsingInfo;
		name = token.getName();
		symbolTable.setSymbolType(name, ESymbolType.eProgramName);
		return parsingInfo;
	}
	public String getName() {
		return name;
	}
	public HeaderSegment getHeaderSegment() {
		return headerSegment;
	}
	public DataSegment getDataSegment() {
		return dataSegment;
	}
	public CodeSegment getCodeSegment() {
		return codeSegment;
	}
	public void setHeaderSegment(HeaderSegment headerSegment) {
		this.headerSegment = headerSegment;
	}
	public void setDataSegment(DataSegment dataSegment) {
		this.dataSegment = dataSegment;
	}
	public void setCodeSegment(CodeSegment codeSegment) {
		this.codeSegment = codeSegment;
	}
	@Override
	public GenerateInfo getCode(int instCount, GenerateInfo generateInfo) {
		return codeSegment.getCode(instCount, generateInfo);
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[Program Name]:"+name+"\n");
		buffer.append(headerSegment);
		buffer.append(dataSegment);
		buffer.append(codeSegment);
		return buffer.toString();
	}
}
