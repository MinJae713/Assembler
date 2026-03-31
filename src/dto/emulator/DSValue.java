package dto.emulator;

import msModel.assembler.node.entity.DataAlloc;

public class DSValue {
	private DataAlloc alloc;
	private int value;
	public DSValue(DataAlloc alloc, int value) {
		this.alloc = alloc;
		this.value = value;
	}
	public DataAlloc getAlloc() {
		return alloc;
	}
	public int getValue() {
		return value;
	}
	public void setAlloc(DataAlloc alloc) {
		this.alloc = alloc;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return alloc+", "+value;
	}
}
