package dto.common.mI.oneOp;
import java.awt.Color;
import java.util.List;

import dto.common.mI.EInstructionSet;
import dto.common.mI.MInstruction;
import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msModel.assembler.symbolTable.ESymbolType;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;
import regiEnums.EAddrMemoryRegisters;

public abstract class MIOneOp extends MInstruction {
	protected String op1;
	protected SymbolTable symbolTable;
	public MIOneOp(SymbolTable symbolTable, 
			EInstructionSet instType, String op1) {
		super(instType);
		this.symbolTable = symbolTable;
		this.op1 = op1;
	}
	@Override
	public String getOp1() {
		return op1;
	}
	@Override
	public String getOp2() {
		return "0";
	}
	@Override
	protected void irToCU(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		super.irToCU(curEmul, cpu, " "+op1);
	}
	@Override
	public String toString() {
		return super.toString()+", op1:"+op1;
	}
	@Override
	protected String getDataAllocAddr(String name, boolean isHex) {
		Symbol selSymbol = null;
		List<Symbol> symbols = symbolTable.getSymbolTable();
		for (Symbol symbol : symbols)
			if (symbol.getSymbolType() == ESymbolType.eData
					&& symbol.getName().equals(name))
				selSymbol = symbol;
		int addr = selSymbol.getOffset();
		return getConstant(addr+"", isHex);
	}
	@Override
	public String getOp1Hex() {
		Symbol symbol = symbolTable.getSymbolFromName(op1);
		int offset = symbol.getOffset();
		return getConstant(offset+"", true);
	}
	@Override
	public String getOp1Binary() {
		Symbol symbol = symbolTable.getSymbolFromName(op1);
		int offset = symbol.getOffset();
		return getConstant(offset+"", false);
	}
	protected void setNewPC(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		initView(curEmul);
		int pc = Integer.parseInt(getOp1Hex(), 16);
		setPC(curEmul, cpu, pc);
		cpu.setCsIdx(cpu.getBus().getCSIdxFromAddress(pc));
	}
	protected void setPC(EmulInfo curEmul, CPU cpu, int pc) 
			throws IllegalAccessException {
		int[] addrMemRegister = cpu.getAddrMemoryRegister();
		int idx = EAddrMemoryRegisters.PC.ordinal();
		cpu.setRegiValue(addrMemRegister, idx, pc);
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		cpu.updateRegiValue(regiValues, idx, pc, Color.RED);
	}
}
