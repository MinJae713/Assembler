package msModel.assembler.node.entity;

import dto.emulator.DSValue;
import msConstants.ModelConsts;

public class DataAlloc {
	private String name;
	private int size;
	private int offset;
	public DataAlloc(String name, int size, int offset) {
		this.name = name;
		this.size = size;
		this.offset = offset;
	}
	public String getName() {
		return name;
	}
	public int getSize() {
		return size;
	}
	public int getOffset() {
		return offset;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public DSValue getInitDSValue() {
		return new DSValue(this, ModelConsts.dsValueInit);
	}
	@Override
	public String toString() {
		return offset+", "+name+", "+size;
	}
}
