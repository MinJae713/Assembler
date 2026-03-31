package dto.common.mI.noOp;

import java.awt.Color;

import dto.common.mI.EInstructionSet;
import dto.emulator.EmulInfo;
import msModel.emulator.CPU;

public class MIHart extends MINoOp {
	private enum EProcedure {
		MAR,
		MARtoIR,
		IRtoCU,
		end
	}
	public MIHart() {
		super(EInstructionSet.HALT);
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
			cpu.getFinishObserver().setFinishProcess(true);
			break;
		}
		return curEmul;
	}
	@Override
	public int getProcessCount() {
		return EProcedure.values().length;
	}
}
