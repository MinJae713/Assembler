package msModel.emulator;

import java.util.Vector;

import dto.common.mI.MInstruction;
import dto.emulator.DSValue;
import dto.emulator.ProcessControlBlock;

public class Bus {
	private Memory memory;
	public void initialize() {}
	public ProcessControlBlock getPCB() {
		return memory.getPCB();
	}
	public void associate(Memory memory) {
		this.memory = memory;
	}
	public MInstruction loadInstruction(int curAddress) {
		return memory.loadInstruction(curAddress);
	}
	public int loadData(int dsIdx) {
		return memory.loadData(dsIdx);
	}
	public DSValue saveData(int idx, int value) {
		return memory.saveData(idx, value);
	}
	public int getSegmentOffset(String seg) {
		return memory.getSegmentOffset(seg);
	}
	public int getDSIdxFromAddress(int address) {
		return memory.getDSIdxFromAddress(address);
	}
	public int getCSIdxFromAddress(int address) {
		return memory.getCSIdxFromAddress(address);
	}
	public Vector<MInstruction> getCs() {
		return memory.getCs();
	}
	public Vector<DSValue> getDs() {
		return memory.getDs();
	}
}
