package dto.common.mI.twoOp.twoRegister;
import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.common.mI.twoOp.MITwoOp;
import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;

public abstract class MITwoRegister extends MITwoOp {
	protected boolean isNF;
	public MITwoRegister(SymbolTable symbolTable, 
			EInstructionSet instType, String op1, String op2) {
		super(symbolTable, instType, op1, op2);
	}
	@Override
	public String getOp1Hex() {
		return getRegiCode(op1, true);
	}
	@Override
	public String getOp2Hex() {
		return getRegiCode(op2, true);
	}
	@Override
	public String getOp1Binary() {
		return getRegiCode(op1, false);
	}
	@Override
	public String getOp2Binary() {
		return getRegiCode(op2, false);
	}
	protected abstract void operate(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException;
	protected void initStatus(EmulInfo curEmul, CPU cpu) {
		int statusSize = cpu.getStatusRegister().length;
		cpu.setStatusRegister(new int[statusSize]);
		for (int i=0; i<statusSize; i++)
			curEmul.setStatusValue(0, i);
	}
	protected void setOneStatus(EmulInfo curEmul, 
			CPU cpu, int idx, boolean status) 
			throws IllegalAccessException {
		int[] statusRegister = cpu.getStatusRegister();
		cpu.setRegiValue(statusRegister, idx, status ? 1 : 0);
		RegiValues regiValues = curEmul.getStatusRegisters();
		cpu.updateRegiValue(regiValues, idx, status ? 1 : 0, Color.RED);
	}
}
