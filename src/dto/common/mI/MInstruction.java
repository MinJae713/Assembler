package dto.common.mI;

import java.awt.Color;

import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ModelConsts;
import msModel.assembler.codeGenerator.ERegister;
import msModel.emulator.CPU;
import regiEnums.EAddrMemoryRegisters;

public abstract class MInstruction {
	protected EInstructionSet instType;

	public MInstruction(EInstructionSet instType) {
		this.instType = instType;
	}
	public EInstructionSet getInstType() {
		return instType;
	}
	public abstract String getOp1();
	public abstract String getOp2();
	public String toHex() {
		String opCode = getOpCode(instType, true);
		return "0x"+opCode+getOp1Hex()+getOp2Hex()+getConstant("0", true);
	}
	public String toBinary() {
		String opCode = getOpCode(instType, false);
		return "0b"+opCode+getOp1Binary()+getOp2Binary()+getConstant("0", false);
	}
	@Override
	public String toString() {
		return "MI - type:"+instType;
	}
	protected String getDataAllocAddr(String name, boolean isHex) {
		return null;
	}
	protected String getConstant(String constant, boolean isHex) {
		int num = Integer.parseInt(constant);
		String hex = isHex ? Integer.toHexString(num)
				: Integer.toBinaryString(num);
		int length = isHex ? 2 : 8;
		for (int i = hex.length(); i<length; i++)
			hex = "0"+hex;
		return hex;
	}
	protected String getRegiCode(String register, boolean isHex) {
		int regiNumber = -1;
		for (ERegister eRegister : ERegister.values())
			if (eRegister.name().equals(register))
				regiNumber = eRegister.ordinal();
		String regiCode = isHex ? Integer.toHexString(regiNumber) 
				: Integer.toBinaryString(regiNumber);
		int length = isHex ? 2 : 8;
		for (int i = regiCode.length(); i<length; i++)
			regiCode = "0"+regiCode;
		return regiCode;
	}
	public String getOpCode(EInstructionSet opType, boolean isHex) {
		String opCode = isHex ? Integer.toHexString(opType.ordinal()) 
				: Integer.toBinaryString(opType.ordinal());
		int length = isHex ? 2 : 8;
		for (int i = opCode.length(); i<length; i++)
			opCode = "0"+opCode;
		return opCode;
	}
	public String getOp1Hex() {
		return getConstant("0", true);
	}
	public String getOp2Hex() {
		return getConstant("0", true);
	}
	public String getOp1Binary() {
		return getConstant("0", false);
	}
	public String getOp2Binary() {
		return getConstant("0", false);
	}
	public String getConvertToHex() {
		return instType+" "+getOp1()+" "+getOp2()+" => "+toHex();
	}
	public abstract EmulInfo nextProcess(EmulInfo emulInfo, 
			int processCount, CPU cpu, int curAddress) throws IllegalAccessException;
	public abstract int getProcessCount(); 
	
	protected void initMAR(EmulInfo curEmul, 
			CPU cpu, int curAddress, Color color) 
			throws IllegalAccessException {
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.MAR.ordinal();
		cpu.setRegiValue(addrMemRegister, idx, curAddress);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, curAddress, color);
	}
	
	protected void marToIR(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.MAR.ordinal();
		int mar = cpu.getAddrMemoryRegister()[idx];
		cpu.setRegiValue(addrMemRegister, idx, 0);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, 0, Color.BLACK);
		
		idx = EAddrMemoryRegisters.IR.ordinal();
		cpu.setRegiValue(addrMemRegister, idx, mar);
		cpu.updateRegiValue(regiValues, idx, mar, Color.RED);
	}
	
	protected void irToCU(EmulInfo curEmul, CPU cpu, String cuMessage) 
			throws IllegalAccessException {
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.IR.ordinal();
		cpu.setRegiValue(addrMemRegister, idx, 0);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, 0, Color.BLACK);
		
		curEmul.setCuValue("해석 - "+instType+cuMessage);
	}
	protected abstract void irToCU(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException;
	
	protected void endProcess(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		initView(curEmul);
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.PC.ordinal();
		int pc = cpu.getAddrMemoryRegister()[idx];
		pc += 4;
		cpu.setRegiValue(addrMemRegister, idx, pc);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, pc, Color.RED);
		cpu.setCsIdx(cpu.getCsIdx()+1);
	}
	protected void initView(EmulInfo curEmul) {
		initializeGeneralIsInit(curEmul);
		initializeStatusIsInit(curEmul);
		curEmul.setCuValue("");
		curEmul.setAluValue("");
		curEmul.setDsIdx(ModelConsts.clearSelection);
		curEmul.setUpdateDS(null);
	}
	protected void initializeStatusIsInit(EmulInfo curEmul) {
		for (int i=0; i<curEmul.getStatusRegisters().getSize(); i++)
			curEmul.setStatusIsInit(Color.BLACK, i);
	}
	protected void initializeGeneralIsInit(EmulInfo curEmul) {
		for (int i=0; i<curEmul.getGeneralRegisters().getSize(); i++)
			curEmul.setGeneralIsInit(Color.BLACK, i);
	}
}
