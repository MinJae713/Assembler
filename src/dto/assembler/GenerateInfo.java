package dto.assembler;

import java.util.Vector;

import dto.common.mI.MInstruction;
import msConstants.ModelConsts;
import msModel.assembler.node.segments.Instruction;

public class GenerateInfo {
	private Instruction curInst;
	private int instIdx;
	private int firstSymbolIdx;
	private int secondSymbolIdx;
	private Vector<MInstruction> mInstructions;
	public GenerateInfo() {
		curInst = null;
		instIdx = 0;
		firstSymbolIdx = ModelConsts.clearSelection;
		secondSymbolIdx = ModelConsts.clearSelection;
	}
	public int getInstIdx() {
		return instIdx;
	}
	public int getFirstSymbolIdx() {
		return firstSymbolIdx;
	}
	public int getSecondSymbolIdx() {
		return secondSymbolIdx;
	}
	public Instruction getCurInst() {
		return curInst;
	}
	public Vector<MInstruction> getMInstructions() {
		return mInstructions;
	}
	public Instruction getNextInst(Vector<Instruction> instructions) {
		if (instIdx+1 != instructions.size()) {
			Instruction instruction = instructions.get(instIdx+1);
			return instruction;
		} else return null;
	}
	public void setInstIdx(int instIdx) {
		this.instIdx = instIdx;
	}
	public void setFirstSymbolIdx(int symbolIdx) {
		this.firstSymbolIdx = symbolIdx;
	}
	public void setSecondSymbolIdx(int secondSymbolIdx) {
		this.secondSymbolIdx = secondSymbolIdx;
	}
	public void setCurInst(Instruction curInst) {
		this.curInst = curInst;
	}
	public void setMInstructions(Vector<MInstruction> mInstructions) {
		this.mInstructions = mInstructions;
	}
}
