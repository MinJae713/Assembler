package dto.common.mI.twoOp;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.common.mI.oneOp.MIOneOp;
import dto.emulator.DSValue;
import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ModelConsts;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;
import regiEnums.EAddrMemoryRegisters;
import regiEnums.EGeneralRegisters;

public abstract class MITwoOp extends MIOneOp {
	protected String op2;
	protected int dsIdx;
	public MITwoOp(SymbolTable symbolTable, 
			EInstructionSet instType, String op1, String op2) {
		super(symbolTable, instType, op1);
		this.op2 = op2;
	}
	@Override
	public String getOp2() {
		return op2;
	}
	@Override
	protected void irToCU(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		super.irToCU(curEmul, cpu, " "+op1+" "+op2);
	}
	@Override
	public String toString() {
		return super.toString()+", op2:"+op2;
	}
	protected void setDsIdxFromMAR(EmulInfo curEmul, CPU cpu) {
		int address = getMAR(cpu);
		dsIdx = cpu.getBus().getDSIdxFromAddress(address);
		curEmul.setDsIdx(dsIdx);
	}
	protected void setMAR(EmulInfo curEmul, 
			CPU cpu, String op, Color color) 
			throws IllegalAccessException {
		String opHex = getDataAllocAddr(op, true);
		int value = Integer.parseInt(opHex, 16);
		value += cpu.getBus().getSegmentOffset(ModelConsts.dsName);
		initMAR(curEmul, cpu, value, color);
	}
	protected void setMBR(EmulInfo curEmul, 
			CPU cpu, int op, Color color) 
			throws IllegalAccessException {
		initMBR(curEmul, cpu, op, color);
	}
	protected void initMBR(EmulInfo curEmul, 
			CPU cpu, int value, Color color) 
			throws IllegalAccessException {
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.MBR.ordinal();
		cpu.setRegiValue(addrMemRegister, idx, value);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, value, color);
	}
	protected void selectGeneralRegiValue(EmulInfo curEmul, 
			CPU cpu, String op, Color color) {
		EGeneralRegisters[] registers = EGeneralRegisters.values();
		RegiValues generalRegi = curEmul.getGeneralRegisters();
		int[] genRegi = cpu.getGeneralRegisters();
		for (int i=0; i<registers.length; i++) {
			String regi = registers[i].name().toLowerCase();
			if (regi.equals(op)) 
				cpu.updateRegiValue(generalRegi, i, genRegi[i], color);
		}
	}
	protected void updateGeneralRegi(EmulInfo curEmul, 
			CPU cpu, int value, String op, Color color) 
			throws IllegalAccessException {
		EGeneralRegisters[] registers = EGeneralRegisters.values();
		RegiValues generalRegi = curEmul.getGeneralRegisters();
		int[] genRegi = cpu.getGeneralRegisters();
		for (int i=0; i<registers.length; i++) {
			String regi = registers[i].name().toLowerCase();
			if (regi.equals(op)) {
				cpu.setRegiValue(genRegi, i, value);
				cpu.updateRegiValue(generalRegi, i, value, color);
			}
		}
	}
	protected int getGeneralRegiValue(CPU cpu, String op) {
		int value = ModelConsts.clearSelection;
		EGeneralRegisters[] registers = EGeneralRegisters.values();
		for (int i=0; i<registers.length; i++) {
			String regi = registers[i].name().toLowerCase();
			if (regi.equals(op)) 
				value = cpu.getGeneralRegisters()[i];
		}
		return value;
	}
	protected void updateDsFromMBR(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		int value = getMBR(cpu);
		DSValue saved = cpu.getBus().saveData(dsIdx, value);
		curEmul.setUpdateDS(saved);
		curEmul.setDsIdx(dsIdx);
	}
	protected int getMBR(CPU cpu) {
		int idx = EAddrMemoryRegisters.MBR.ordinal();
		int value = cpu.getAddrMemoryRegister()[idx];
		return value;
	}
	protected int getMAR(CPU cpu) {
		int idx = EAddrMemoryRegisters.MAR.ordinal();
		int address = cpu.getAddrMemoryRegister()[idx];
		return address;
	}
}
