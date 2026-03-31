package dto.common.mI.twoOp.twoRegister;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;

public class MIMove extends MITwoRegister {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		getRegi,
		moveToRegi,
		end
	}
	public MIMove(SymbolTable symbolTable, String op1, String op2) {
		super(symbolTable, EInstructionSet.MOV, op1, op2);
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
		case moveToRegi:
			operate(curEmul, cpu);
			break;
		case end:
			endProcess(curEmul, cpu);
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
		int value = getGeneralRegiValue(cpu, op2);
		updateGeneralRegi(curEmul, cpu, value, op1, Color.RED);
	}
}
