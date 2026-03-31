package msModel.assembler.node.segments;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import dto.common.mI.MInstruction;
import dto.common.mI.noOp.MIHart;
import dto.common.mI.oneOp.MIGe;
import dto.common.mI.oneOp.MIJump;
import dto.common.mI.twoOp.MILoadA;
import dto.common.mI.twoOp.MILoadC;
import dto.common.mI.twoOp.MIStoreA;
import dto.common.mI.twoOp.MIStoreC;
import dto.common.mI.twoOp.twoRegister.MIAdd;
import dto.common.mI.twoOp.twoRegister.MICompare;
import dto.common.mI.twoOp.twoRegister.MIMove;
import msConstants.ModelConsts;
import msModel.assembler.CodeGetter;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.lexicalAnalyzer.Token;
import msModel.assembler.node.Node;
import msModel.assembler.node.entity.ECommand;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;

public class Instruction extends Node implements CodeGetter{
	private enum EOperandType {
		eConstant, eRegister, eVariable
	}
	private ECommand eCommand;
	private String operand[];
	private int offset;
	public Instruction(LexicalAnalyzer lexicalAnalyzer, 
			SymbolTable symbolTable, int offset) {
		super(lexicalAnalyzer, symbolTable);
		eCommand = null;
		this.operand = new String[2];
		Arrays.fill(operand, null);
		this.offset = offset;
	}
	@Override
	public ParsingInfo parse(Token token, ParsingInfo parsingInfo) throws Exception {
		if (token.getSymbolType() == ESymbolType.eLabel) {
			eCommand = ECommand.eLabel;
			operand[0] = token.getName();
			symbolTable.setSymbolOffset(token.getName(), offset);
			return parsingInfo;
		}
		if (eCommand == null) {
			eCommand = getECommandFromToken(token);
			symbolTable.setSymbolType(eCommand.getText(), ESymbolType.eInstruction);
			symbolTable.setSymbolSize(eCommand.getText(), 4); 
		} else if (operand[0] == null) operand[0] = token.getName();
		else if (operand[1] == null) operand[1] = token.getName();
		return parsingInfo;
	}
	private ECommand getECommandFromToken(Token token) {
		for (ECommand command : ECommand.values())
			if (command.getText().equals(token.getName()))
				return command;
		return null;
	}
	public ECommand getECommand() {
		return eCommand;
	}
	public String[] getOperand() {
		return operand;
	}
	public int getOffset() {
		return offset;
	}
	public void setECommand(ECommand eCommand) {
		this.eCommand = eCommand;
	}
	public void setOperand(String[] operand) {
		this.operand = operand;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	@Override
	public GenerateInfo getCode(int instCount, 
			GenerateInfo generateInfo) {
		generateInfo.setCurInst(this);
		generateInfo.setInstIdx(instCount);
		Vector<MInstruction> mInstructions = new Vector<MInstruction>();
		int firstIdx = symbolTable.getSymbolIdxFromName(operand[0]);
		int secondIdx = symbolTable.getSymbolIdxFromName(operand[1]);
		if (firstIdx > -1) generateInfo.setFirstSymbolIdx(firstIdx);
		if (secondIdx > -1) generateInfo.setSecondSymbolIdx(secondIdx);
		if (eCommand == ECommand.eHalt)
			mInstructions.add(new MIHart());
		else if (eCommand == ECommand.eJump) 
			mInstructions.add(new MIJump(symbolTable, operand[0]));
		else if (eCommand == ECommand.eGE) 
			mInstructions.add(new MIGe(symbolTable, operand[0]));
		else {
			EOperandType op1Type = getOperandType(operand[0]);
			EOperandType op2Type = getOperandType(operand[1]);
			if (eCommand == ECommand.eAdd) {
				if (op2Type == EOperandType.eRegister)
					mInstructions.add(new MIAdd(symbolTable, operand[0], operand[1]));
				else if (op2Type == EOperandType.eVariable) {
					String nextRegister = getNextRegister(operand[0]);
					mInstructions.add(new MILoadA(symbolTable, nextRegister, operand[1]));
					mInstructions.add(new MIAdd(symbolTable, operand[0], nextRegister));
				}
				else if (op2Type == EOperandType.eConstant) {
					String nextRegister = getNextRegister(operand[0]);
					mInstructions.add(new MILoadC(symbolTable, nextRegister, operand[1]));
					mInstructions.add(new MIAdd(symbolTable, operand[0], nextRegister));
				} else throw new IllegalArgumentException(ModelConsts.addException);
			} else if (eCommand == ECommand.eCmp) {
				if (op1Type == EOperandType.eRegister) {
					if (op2Type == EOperandType.eRegister) 
						mInstructions.add(new MICompare(symbolTable, operand[0], operand[1]));
					else if (op2Type == EOperandType.eConstant) {
						String nextRegister = getNextRegister(operand[0]);
						mInstructions.add(new MILoadC(symbolTable, nextRegister, operand[1]));
						mInstructions.add(new MICompare(symbolTable, operand[0], nextRegister));
					}
					else if (op2Type == EOperandType.eVariable) {
						String nextRegister = getNextRegister(operand[0]);
						mInstructions.add(new MILoadA(symbolTable, nextRegister, operand[1]));
						mInstructions.add(new MICompare(symbolTable, operand[0], nextRegister));
					} else throw new IllegalArgumentException(ModelConsts.cmpRegiException);
				} else if (op1Type == EOperandType.eVariable) {
					if (op2Type == EOperandType.eRegister) {
						String nextRegister = getNextRegister(operand[1]);
						mInstructions.add(new MILoadA(symbolTable, nextRegister, operand[0]));
						mInstructions.add(new MICompare(symbolTable, nextRegister, operand[1]));
					} else if (op2Type == EOperandType.eConstant) {
						String firstRegister = getNextRegister(operand[0]);
						String secondRegister = getNextRegister(firstRegister);
						mInstructions.add(new MILoadA(symbolTable, firstRegister, operand[0]));
						mInstructions.add(new MILoadC(symbolTable, secondRegister, operand[1]));
						mInstructions.add(new MICompare(symbolTable, firstRegister, secondRegister));
					} else if (op2Type == EOperandType.eVariable) {
						String firstRegister = getNextRegister(operand[0]);
						String secondRegister = getNextRegister(firstRegister);
						mInstructions.add(new MILoadA(symbolTable, firstRegister, operand[0]));
						mInstructions.add(new MILoadA(symbolTable, secondRegister, operand[1]));
						mInstructions.add(new MICompare(symbolTable, firstRegister, secondRegister));
					} else throw new IllegalArgumentException(ModelConsts.cmpVarException);
				} else throw new IllegalArgumentException(ModelConsts.cmpException);
			} else if (eCommand == ECommand.eMove) {
				if (op1Type == EOperandType.eRegister) {
					if (op2Type == EOperandType.eRegister)
						mInstructions.add(new MIMove(symbolTable, operand[0], operand[1]));
					else if (op2Type == EOperandType.eConstant) {
						String nextRegister = getNextRegister(operand[0]);
						mInstructions.add(new MILoadC(symbolTable, nextRegister, operand[1]));
						mInstructions.add(new MIMove(symbolTable, operand[0], nextRegister));
					} else if (op2Type == EOperandType.eVariable)
						mInstructions.add(new MILoadA(symbolTable, operand[0], operand[1]));
					else throw new IllegalArgumentException(ModelConsts.moveRegiException);
				} else if (op1Type == EOperandType.eVariable) {
					if (op2Type == EOperandType.eRegister)
						mInstructions.add(new MIStoreA(symbolTable, operand[0], operand[1]));
					else if (op2Type == EOperandType.eConstant)
						mInstructions.add(new MIStoreC(symbolTable, operand[0], operand[1]));
					else throw new IllegalArgumentException(ModelConsts.moveVarException);
				} else throw new IllegalArgumentException(ModelConsts.moveException);
			} else throw new IllegalArgumentException(ModelConsts.instructionException);
		}
		generateInfo.setMInstructions(mInstructions);
		return generateInfo;
	}
	public int getMICount() {
		int result = 0;
		
		if (eCommand == ECommand.eHalt) result = 1;
		else if (eCommand == ECommand.eJump) result = 1;
		else if (eCommand == ECommand.eGE) result = 1;
		else {
			EOperandType op1Type = getOperandType(operand[0]);
			EOperandType op2Type = getOperandType(operand[1]);
			if (eCommand == ECommand.eAdd) {
				if (op2Type == EOperandType.eRegister) result = 1;
				else if (op2Type == EOperandType.eVariable) result = 2;
				else if (op2Type == EOperandType.eConstant) result = 2;
				else throw new IllegalArgumentException(ModelConsts.addException);
			} else if (eCommand == ECommand.eCmp) {
				if (op1Type == EOperandType.eRegister) {
					if (op2Type == EOperandType.eRegister) result = 1;
					else if (op2Type == EOperandType.eConstant) result = 2;
					else if (op2Type == EOperandType.eVariable) result = 2;
					else throw new IllegalArgumentException(ModelConsts.cmpRegiException);
				} else if (op1Type == EOperandType.eVariable) {
					if (op2Type == EOperandType.eRegister) result = 2;
					else if (op2Type == EOperandType.eConstant) result = 3;
					else if (op2Type == EOperandType.eVariable) result = 3;
					else throw new IllegalArgumentException(ModelConsts.cmpVarException);
				} else throw new IllegalArgumentException(ModelConsts.cmpException);
			} else if (eCommand == ECommand.eMove) {
				if (op1Type == EOperandType.eRegister) {
					if (op2Type == EOperandType.eRegister) result = 1;
					else if (op2Type == EOperandType.eConstant) result = 2;
					else if (op2Type == EOperandType.eVariable) result = 1;
					else throw new IllegalArgumentException(ModelConsts.moveRegiException);
				} else if (op1Type == EOperandType.eVariable) {
					if (op2Type == EOperandType.eRegister) result = 1;
					else if (op2Type == EOperandType.eConstant) result = 1;
					else throw new IllegalArgumentException(ModelConsts.moveVarException);
				} else throw new IllegalArgumentException(ModelConsts.moveException);
			} else throw new IllegalArgumentException(ModelConsts.instructionException);
		}
		return result;
	}
	private String getNextRegister(String operand) {
		String register = "r";
		if (operand.charAt(0) == 'r' && operand.substring(1).matches("[0-9]+")) {
			int nextRegiNum = Integer.parseInt(operand.charAt(1)+"")+1;
			register = register + nextRegiNum;
		} else register = register + "0";
		return register;
	}
	private EOperandType getOperandType(String op) {
		if (isConstant(op)) return EOperandType.eConstant;
		else if (isVariable(op)) return EOperandType.eVariable;
		else if (isRegister(op)) return EOperandType.eRegister;
		throw new IllegalArgumentException(ModelConsts.operandException);
	}
	private boolean isConstant(String op) {
		return op.matches("[0-9]+");
	}
	private boolean isVariable(String op) {
		List<Symbol> symbols = symbolTable.getSymbolTable();
		for (Symbol symbol : symbols)
			if (symbol.getSymbolType() == ESymbolType.eData 
				&& symbol.getName().equals(op)) return true;
		return false;
	}
	private boolean isRegister(String op) {
		List<Symbol> symbols = symbolTable.getSymbolTable();
		for (Symbol symbol : symbols)
			if (symbol.getSymbolType() == ESymbolType.eRegister 
				&& symbol.getName().equals(op)) return true;
		return false;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer(eCommand.getText().toUpperCase()+" ");
		for (String op : operand) 
			if (op != null) buffer.append(op+" ");
		return buffer.toString();
	}
}
