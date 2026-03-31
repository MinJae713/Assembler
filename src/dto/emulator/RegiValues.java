package dto.emulator;

import java.awt.Color;

public class RegiValues {
	private int[] regivalue;
	private Color[] isInits;
	private int size;
	public RegiValues(int[] regivalue, 
			Color[] isInits, int size) {
		this.regivalue = regivalue;
		this.isInits = isInits;
		this.isInits = isInits;
		this.size = size;
	}
	public int[] getRegiValues() {
		return regivalue;
	}
	public Color[] getIsInits() {
		return isInits;
	}
	public int getSize() {
		return size;
	}
	public void setRegivalue(int[] regivalue) {
		this.regivalue = regivalue;
	}
	public void setIsInits(Color[] isInit) {
		this.isInits = isInit;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setOneRegiValue(int idx, 
			int value, Color isInit) {
		regivalue[idx] = value;
		isInits[idx] = isInit;
	}
}
