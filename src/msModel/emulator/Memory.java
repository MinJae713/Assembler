package msModel.emulator;

import java.util.Vector;

import dto.common.ActivationRecord;
import dto.common.HeapObj;
import dto.common.mI.MInstruction;
import dto.emulator.DSValue;
import dto.emulator.ProcessControlBlock;
import msConstants.ModelConsts;
import msModel.assembler.node.entity.DataAlloc;

public class Memory {
	private ProcessControlBlock pcb;
	private Vector<MInstruction> cs;
	private Vector<DSValue> ds;
	private Vector<ActivationRecord> ss;
	private Vector<HeapObj> hs;
	
	public void initialize() {
		pcb = null;
		cs = new Vector<MInstruction>();
		ds = new Vector<DSValue>();
		ss = new Vector<ActivationRecord>();
		hs = new Vector<HeapObj>();
	}
	public ProcessControlBlock getPCB() {
		return pcb;
	}
	public Vector<MInstruction> getCs() {
		return cs;
	}
	public Vector<DSValue> getDs() {
		return ds;
	}
	public Vector<ActivationRecord> getSs() {
		return ss;
	}
	public Vector<HeapObj> getHs() {
		return hs;
	}
	public void setPCB(ProcessControlBlock pcb) {
		this.pcb = pcb;
		setCs(pcb.getMInstructions());
		setDs(pcb.getDsValues());
	}
	public void setCs(Vector<MInstruction> cs) {
		this.cs = cs;
	}
	public void setDs(Vector<DSValue> ds) {
		this.ds = ds;
	}
	public void setSs(Vector<ActivationRecord> ss) {
		this.ss = ss;
	}
	public void setHs(Vector<HeapObj> hs) {
		this.hs = hs;
	}
	public MInstruction loadInstruction(int curAddress) {
		int csOffset = getSegmentOffset(ModelConsts.csName);
		int csSize = getSegmentSize(ModelConsts.csName);
		if (curAddress < csOffset || curAddress >= csOffset+csSize)
			return null;
		else return cs.get(curAddress/4);
	}
	public int loadData(int dsIdx) {
		if (dsIdx >= ds.size())
			throw new IllegalArgumentException(ModelConsts.indexOutBoundException);
		return ds.get(dsIdx).getValue();
	}
	public DSValue saveData(int dsIdx, int value) {
		if (dsIdx >= ds.size())
			throw new IllegalArgumentException(ModelConsts.indexOutBoundException);
		ds.get(dsIdx).setValue(value);
		return ds.get(dsIdx);
	}
	private int getSegmentSize(String seg) {
		Vector<DataAlloc> headerSeg = pcb.getHeaderSegment();
		for (DataAlloc alloc : headerSeg)
			if (alloc.getName().equals(seg)) return alloc.getSize();
		throw new IllegalArgumentException(seg+ModelConsts.segNameException);
	}
	public int getSegmentOffset(String seg) {
		Vector<DataAlloc> headerSeg = pcb.getHeaderSegment();
		for (DataAlloc alloc : headerSeg)
			if (alloc.getName().equals(seg)) return alloc.getOffset();
		throw new IllegalArgumentException(seg+ModelConsts.segNameException);
	}
	public int getDSIdxFromAddress(int address) {
		Vector<DataAlloc> allocs = pcb.getHeaderSegment();
		int dsOffset = -1;
		for (DataAlloc alloc : allocs)
			if (alloc.getName().equals(ModelConsts.dsName))
				dsOffset = alloc.getOffset();
		for (int i=0; i<ds.size(); i++) 
			if (dsOffset+ds.get(i).getAlloc().getOffset() == address) return i;
		throw new IllegalArgumentException(ModelConsts.noAddressException);
	}
	public int getCSIdxFromAddress(int address) {
		Vector<DataAlloc> allocs = pcb.getHeaderSegment();
		int csOffset = -1;
		for (DataAlloc alloc : allocs)
			if (alloc.getName().equals(ModelConsts.csName))
				csOffset = alloc.getOffset();
		for (int i=0; i<cs.size(); i++) 
			if ((csOffset+i*4) == address) return i;
		throw new IllegalArgumentException(ModelConsts.noAddressException);
	}
}
