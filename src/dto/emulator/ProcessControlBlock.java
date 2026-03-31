package dto.emulator;

import java.awt.Color;
import java.util.Vector;

import dto.common.mI.MInstruction;
import msConstants.ModelConsts;
import msModel.assembler.node.entity.DataAlloc;
import regiEnums.EAddrMemoryRegisters;
import regiEnums.EBaseRegisters;

public class ProcessControlBlock {
	private Vector<DataAlloc> headerSegment;
	private Vector<DataAlloc> dataSegment;
	private Vector<MInstruction> mInstructions;
	public ProcessControlBlock(Vector<DataAlloc> headerSegment, 
			Vector<DataAlloc> dataSegment,
			Vector<MInstruction> mInstructions) {
		this.headerSegment = headerSegment;
		this.dataSegment = dataSegment;
		this.mInstructions = mInstructions;
	}
	public Vector<DataAlloc> getHeaderSegment() {
		return headerSegment;
	}
	public Vector<DataAlloc> getDataSegment() {
		return dataSegment;
	}
	public Vector<MInstruction> getMInstructions() {
		return mInstructions;
	}
	public EmulInfo getEmulInfo() {
		EmulInfo emulInfo = new EmulInfo();
		for (DataAlloc alloc : headerSegment) {
			if (alloc.getName().equals(ModelConsts.csName))
				emulInfo.setCsAlloc(alloc);
			else if (alloc.getName().equals(ModelConsts.dsName))
				emulInfo.setDsAlloc(alloc);
			else if (alloc.getName().equals(ModelConsts.ssName))
				emulInfo.setSsAlloc(alloc);
			else if (alloc.getName().equals(ModelConsts.hsName))
				emulInfo.setHsAlloc(alloc);
		}
		emulInfo.setCs(mInstructions);
		emulInfo.setDs(getDsValues());
		emulInfo.setBaseValue(emulInfo.getCsAlloc().getOffset(), 
				EBaseRegisters.CS.ordinal());
		emulInfo.setBaseValue(emulInfo.getDsAlloc().getOffset(), 
				EBaseRegisters.DS.ordinal());
		emulInfo.setBaseValue(emulInfo.getSsAlloc().getOffset(), 
				EBaseRegisters.SS.ordinal());
		emulInfo.setBaseValue(emulInfo.getHsAlloc().getOffset(), 
				EBaseRegisters.HS.ordinal());
		emulInfo.setBaseIsInit(Color.BLACK, EBaseRegisters.CS.ordinal());
		emulInfo.setBaseIsInit(Color.BLACK, EBaseRegisters.DS.ordinal());
		emulInfo.setBaseIsInit(Color.BLACK, EBaseRegisters.SS.ordinal());
		emulInfo.setBaseIsInit(Color.BLACK, EBaseRegisters.HS.ordinal());
		emulInfo.setAddrMemValue(emulInfo.getSsAlloc().getOffset(),
				EAddrMemoryRegisters.SP.ordinal());
		emulInfo.setAddrMemIsInit(Color.BLACK, EAddrMemoryRegisters.SP.ordinal());
		return emulInfo;
	}
	public Vector<DSValue> getDsValues() {
		Vector<DSValue> dsValues = new Vector<DSValue>();
		for (DataAlloc alloc : dataSegment)
			dsValues.add(alloc.getInitDSValue());
		return dsValues;
	}
}
