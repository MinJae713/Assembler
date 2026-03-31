package dto.common.mI.twoOp;
import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msConstants.ModelConsts;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;

public class MIStoreC extends MITwoOp {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		MARMBR,
		getDS,
		updateDS,
		end
	}
	public MIStoreC(SymbolTable symbolTable, 
			String op1, String op2) {
		super(symbolTable, EInstructionSet.STRC, op1, op2);
	}
	@Override
	public String getOp1Hex() {
		return getDataAllocAddr(op1, true);
	}
	@Override
	public String getOp2Hex() {
		return getConstant(op2, true);
	}
	@Override
	public String getOp1Binary() {
		return getDataAllocAddr(op1, false);
	}
	@Override
	public String getOp2Binary() {
		return getConstant(op2, false);
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
		case MARMBR: 
			setMAR(curEmul, cpu, op1, Color.RED);
			setMBR(curEmul, cpu, Integer.parseInt(op2), Color.RED);
			break;
		case getDS: 
			setDsIdxFromMAR(curEmul, cpu);
			break;
		case updateDS: 
			initMAR(curEmul, cpu, ModelConsts.clearSelection, Color.BLACK);
			updateDsFromMBR(curEmul, cpu);
			initMBR(curEmul, cpu, ModelConsts.clearSelection, Color.BLACK);
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
}
