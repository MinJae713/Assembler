package dto.common.mI.noOp;

import dto.common.mI.EInstructionSet;
import dto.common.mI.MInstruction;
import dto.emulator.EmulInfo;
import msModel.emulator.CPU;

public abstract class MINoOp extends MInstruction {

	public MINoOp(EInstructionSet instructionSet) {
		super(instructionSet);
	}
	@Override
	public String getOp1() {
		return "0";
	}
	@Override
	public String getOp2() {
		return "0";
	}
	@Override
	protected void irToCU(EmulInfo curEmul, CPU cpu) 
			throws IllegalAccessException {
		super.irToCU(curEmul, cpu, "");
	}
}
