package dto.common.mI.oneOp;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;
import regiEnums.EStatusRegisters;

public class MIGe extends MIOneOp {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		checkNF,
		end
	}
	public MIGe(SymbolTable symbolTable, String op1) {
		super(symbolTable, EInstructionSet.GE, op1);
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
		case checkNF:
			int idx = EStatusRegisters.NF.ordinal();
			curEmul.setStatusIsInit(Color.RED, idx);
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
		int idx = EStatusRegisters.NF.ordinal();
		int nfVal = cpu.getStatusRegister()[idx];
		if (nfVal == 1) super.endProcess(curEmul, cpu);
		else setNewPC(curEmul, cpu);
	}
}
