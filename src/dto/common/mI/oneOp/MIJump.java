package dto.common.mI.oneOp;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;

public class MIJump extends MIOneOp {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		end
	}
	public MIJump(SymbolTable symbolTable, String op1) {
		super(symbolTable, EInstructionSet.JMP, op1);
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
	protected void endProcess(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		setNewPC(curEmul, cpu);
	}
}
