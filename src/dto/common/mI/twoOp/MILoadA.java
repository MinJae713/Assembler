package dto.common.mI.twoOp;
import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msConstants.ModelConsts;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;

public class MILoadA extends MITwoOp {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		MARRegi,
		MARtoDS,
		DStoMBR,
		MBRRegi,
		MBRtoRegi,
		end
	}
	public MILoadA(SymbolTable symbolTable, 
			String op1, String op2) {
		super(symbolTable, EInstructionSet.LDRA, op1, op2);
	}
	@Override
	public String getOp1Hex() {
		return getRegiCode(op1, true);
	}
	@Override
	public String getOp2Hex() {
		return getDataAllocAddr(op2, true);
	}
	@Override
	public String getOp1Binary() {
		return getRegiCode(op1, false);
	}
	@Override
	public String getOp2Binary() {
		return getDataAllocAddr(op2, false);
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
		case MARRegi: 
			setMAR(curEmul, cpu, op2, Color.RED);
			break;
		case MARtoDS: 
			setDsIdxFromMAR(curEmul, cpu); 
			initMAR(curEmul, cpu, ModelConsts.clearSelection, Color.BLACK);
			break;
		case DStoMBR: 
			curEmul.setDsIdx(ModelConsts.clearSelection);
			int value = cpu.getBus().loadData(dsIdx);
			setMBR(curEmul, cpu, value, Color.RED);
			break;
		case MBRRegi: 
			selectGeneralRegiValue(curEmul, cpu, op1, Color.CYAN);
			break;
		case MBRtoRegi: 
			value = getMBR(cpu);
			updateGeneralRegi(curEmul, cpu, value, op1, Color.RED);
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
