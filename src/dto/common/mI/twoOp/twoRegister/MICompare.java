package dto.common.mI.twoOp.twoRegister;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.assembler.symbolTable.SymbolTable;
import msModel.emulator.CPU;
import regiEnums.EStatusRegisters;

public class MICompare extends MITwoRegister {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		getRegi,
		ALU,
		setNF,
		end
	}
	public MICompare(SymbolTable symbolTable, String op1, String op2) {
		super(symbolTable, EInstructionSet.CMP, op1, op2);
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
			selectGeneralRegiValue(curEmul, cpu, op1, Color.RED);
			selectGeneralRegiValue(curEmul, cpu, op2, Color.RED);
			break;
		case ALU: 
			initializeGeneralIsInit(curEmul);
			operate(curEmul, cpu);
			break;
		case setNF:
			initStatus(curEmul, cpu);
			setOneStatus(curEmul, cpu, EStatusRegisters.NF.ordinal(), isNF);
			break;
		case end:
			endProcess(curEmul, cpu);
			break;
		}
		return curEmul;
	}
	@Override
	protected void operate(EmulInfo curEmul, CPU cpu) {
		int op1Val = getGeneralRegiValue(cpu, op1);
		int op2Val = getGeneralRegiValue(cpu, op2);
		isNF = op1Val-op2Val < 0;
		String result = isNF ? "음수" : "0 또는 양수";
		curEmul.setAluValue("연산 - "+op1Val+"-"+op2Val+" => 연산 결과 - "+result);
	}
	@Override
	public int getProcessCount() {
		return EProcedure.values().length;
	}
}
