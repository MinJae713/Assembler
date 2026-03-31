package dto.common.mI.twoOp.twoRegister;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;
import regiEnums.EStatusRegisters;

public class MIAdd extends MITwoRegister{
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		getRegi,
		ALU,
		updateStatus,
		updateBase,
		end
	}
	private int value;
	private boolean isZF;
	private boolean isOF;
	private boolean isCF;
	public MIAdd(SymbolTable symbolTable, String op1, String op2) {
		super(symbolTable, EInstructionSet.ADD, op1, op2);
	}
	@Override
	public EmulInfo nextProcess(EmulInfo curEmul, 
			int processCount, CPU cpu, int curAddress) 
					throws IllegalAccessException {
		EProcedure procedure = EProcedure.values()[processCount];
		switch (procedure) {
		case MAR:
			initMAR(curEmul, cpu, curAddress, Color.RED);
			break;
		case MARtoIR:
			marToIR(curEmul, cpu);
			break;
		case IRtoCU:
			irToCU(curEmul, cpu);
			break;
		case getRegi:
			selectGeneralRegiValue(curEmul, cpu, op1, Color.CYAN);
			selectGeneralRegiValue(curEmul, cpu, op2, Color.RED);
			break;
		case ALU: 
			initializeGeneralIsInit(curEmul);
			operate(curEmul, cpu);
			break;
		case updateStatus: 
			initStatus(curEmul, cpu);
			setOneStatus(curEmul, cpu, EStatusRegisters.ZF.ordinal(), isZF);
			setOneStatus(curEmul, cpu, EStatusRegisters.CF.ordinal(), isCF);
			setOneStatus(curEmul, cpu, EStatusRegisters.NF.ordinal(), isNF);
			setOneStatus(curEmul, cpu, EStatusRegisters.OF.ordinal(), isOF);
			break;
		case updateBase: 
			initializeStatusIsInit(curEmul);
			updateGeneralRegi(curEmul, cpu, value, op1, Color.RED); 
			break;
		case end:
			endProcess(curEmul, cpu);
			break;
		}
		return curEmul;
	}

	@Override
	public int getProcessCount() {
		return EProcedure.values().length;
	}
	@Override
	protected void operate(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		int op1Val = getGeneralRegiValue(cpu, op1);
		int op2Val = getGeneralRegiValue(cpu, op2);
		value = op1Val+op2Val;
		isZF = value == 0;
		isNF = value < 0;
		isOF = Integer.toBinaryString(value).length() > 8;
		isCF = getCarryFlaged(op1Val, op2Val);
		StringBuffer buffer = new StringBuffer("연산 - "+op1Val+"+"
				+op2Val+"="+value);
		buffer.append("\n\n[Status Register]");
		buffer.append("\nZF : "+isZF);
		buffer.append("\nCF : "+isCF);
		buffer.append("\nNF : "+isNF);
		buffer.append("\nOF : "+isOF);
		curEmul.setAluValue(buffer.toString());
	}
	private boolean getCarryFlaged(int op1Val, int op2Val) {
		String op1Bin = Integer.toBinaryString(op1Val);
		String valBin = Integer.toBinaryString(value);
		return op1Bin.length() != valBin.length();
	}
}
