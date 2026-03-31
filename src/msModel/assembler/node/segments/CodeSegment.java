package msModel.assembler.node.segments;
import java.util.List;
import java.util.Vector;

import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import dto.common.mI.MInstruction;
import msModel.assembler.CodeGetter;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.Node;
import msModel.assembler.node.entity.ECommand;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.SymbolTable;
import status.EParsingStatus;

public class CodeSegment extends Node implements CodeGetter{
	
	private Vector<Instruction> instructions;
	private Vector<MInstruction> mInstructions;
	private Instruction curInstruction;
	private int labelOffset;
	private int instOffset;
	private String nextToken;
	public CodeSegment(LexicalAnalyzer lexicalAnalyzer, SymbolTable symbolTable) {
		super(lexicalAnalyzer, symbolTable);
		instructions = new Vector<Instruction>();
		mInstructions = new Vector<MInstruction>();
		labelOffset = 0;
		instOffset = 0;
	}
	public void setParsingStatus(String nextToken) {
		this.nextToken = nextToken;
	}
	@Override
	public ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception {
		if (token.getSymbolType() == ESymbolType.eSegmentHead) 
			return parsingInfo;
		ECommand eCommand = getECommand(token);
		if (eCommand != null) {
			curInstruction = new Instruction(lexicalAnalyzer, symbolTable, instOffset);
			if (token.getSymbolType() != ESymbolType.eLabel) {
				instructions.add(curInstruction);
			} else curInstruction.setOffset(labelOffset);
		}
		parsingInfo = curInstruction.parse(token, parsingInfo);
		if (getECommand(nextToken) != null && 
				token.getSymbolType() != ESymbolType.eLabel) {
			labelOffset += curInstruction.getMICount()*4;
			instOffset++;
			parsingInfo.setStatus(EParsingStatus.eCode);
			parsingInfo.setInstruction(curInstruction);
		}
		return parsingInfo;
	}
	private ECommand getECommand(Token token) {
		return getECommand(token.getName());
	}
	private ECommand getECommand(String tokenStr) {
		for (ECommand command : ECommand.values())
			if (command.getText().equals(tokenStr))
				return command;
		if (isLabel(tokenStr)) return ECommand.eLabel;
		return null;
	}
	private boolean isLabel(String tokenStr) {
		return tokenStr.charAt(tokenStr.length()-1) == ':';
	}
	public Vector<Instruction> getInstructions() {
		return instructions;
	}
	@Override
	public GenerateInfo getCode(int instCount, 
			GenerateInfo generateInfo) {
		generateInfo = instructions.get(instCount).getCode(instCount, generateInfo);
		List<MInstruction> mInsts = generateInfo.getMInstructions();
		for (MInstruction mInstruction : mInsts) mInstructions.add(mInstruction);
		return generateInfo;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("\n[Code Segment Instructions]\n");
		for (Instruction instruction : instructions)
			buffer.append(instruction);
		return buffer.toString();
	}
	public Vector<MInstruction> getMInstructions() {
		return mInstructions;
	}
}
