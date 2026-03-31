package dto.emulator;

import java.awt.Color;
import java.util.Vector;

import dto.common.ActivationRecord;
import dto.common.HeapObj;
import dto.common.mI.MInstruction;
import msConstants.ModelConsts;
import msModel.assembler.node.entity.DataAlloc;
import regiEnums.EAddrMemoryRegisters;
import regiEnums.EBaseRegisters;
import regiEnums.EGeneralRegisters;
import regiEnums.EStatusRegisters;

public class EmulInfo {
	private RegiValues generalRegisters;
	private RegiValues baseRegisters;
	private RegiValues addrMemSpRegisters;
	private String cuValue;
	private String aluValue;
	private RegiValues statusRegisters;
	
	private DataAlloc csAlloc; 
	private Vector<MInstruction> cs;
	private DataAlloc dsAlloc; 
	private Vector<DSValue> ds;
	private DSValue updateDS; 
	private DataAlloc ssAlloc; 
	private Vector<ActivationRecord> ss;
	private DataAlloc hsAlloc; 
	private Vector<HeapObj> hs;
	private int csIdx;
	private int dsIdx;
	private int ssIdx;
	private int hsIdx;
	
	public EmulInfo() {
		int generalLength = EGeneralRegisters.values().length;
		int baseLength = EBaseRegisters.values().length;
		int addrMemLength = EAddrMemoryRegisters.values().length;
		int statusLength = EStatusRegisters.values().length;
		
		generalRegisters = new RegiValues(new int[generalLength], 
				new Color[generalLength], generalLength);
		baseRegisters = new RegiValues(new int[baseLength], 
				new Color[baseLength], baseLength);
		addrMemSpRegisters = new RegiValues(new int[addrMemLength], 
				new Color[addrMemLength], addrMemLength);
		statusRegisters = new RegiValues(new int[statusLength], 
				new Color[statusLength], statusLength);
		
		cuValue = "";
		aluValue = "";
		
		cs = new Vector<MInstruction>();
		ds = new Vector<DSValue>();
		updateDS = null;
		ss = new Vector<ActivationRecord>();
		hs = new Vector<HeapObj>();
		csIdx = ModelConsts.clearSelection;
		dsIdx = ModelConsts.clearSelection;
		ssIdx = ModelConsts.clearSelection;
		hsIdx = ModelConsts.clearSelection;
	}
	public RegiValues getGeneralRegisters() {
		return generalRegisters;
	}

	public RegiValues getBaseRegisters() {
		return baseRegisters;
	}

	public RegiValues getAddrMemSpRegisters() {
		return addrMemSpRegisters;
	}

	public RegiValues getStatusRegisters() {
		return statusRegisters;
	}
	
	public int getGeneralValue(int idx) {
		return getRegiValue(idx, generalRegisters.getRegiValues());
	}
	public int getBaseValue(int idx) {
		return getRegiValue(idx, baseRegisters.getRegiValues());
	}
	public int getAddrMemValue(int idx) {
		return getRegiValue(idx, addrMemSpRegisters.getRegiValues());
	}
	public int getStatusValue(int idx) {
		return getRegiValue(idx, statusRegisters.getRegiValues());
	}
	private int getRegiValue(int idx, int[] registers) {
		if (idx >= registers.length)
			throw new IllegalArgumentException(
					ModelConsts.indexOutBoundException);
		return registers[idx];
	}
	public void setGeneralValue(int value, int idx) {
		setRegiValue(value, idx, generalRegisters.getRegiValues());
	}
	public void setBaseValue(int value, int idx) {
		setRegiValue(value, idx, baseRegisters.getRegiValues());
	}
	public void setAddrMemValue(int value, int idx) {
		setRegiValue(value, idx, addrMemSpRegisters.getRegiValues());
	}
	public void setStatusValue(int value, int idx) {
		setRegiValue(value, idx, statusRegisters.getRegiValues());
	}
	private void setRegiValue(int value, int idx, int[] registers) {
		if (idx >= registers.length)
			throw new IllegalArgumentException(
					ModelConsts.indexOutBoundException);
		registers[idx] = value;
	}
	public void setGeneralIsInit(Color value, int idx) {
		setRegiIsInits(value, idx, generalRegisters.getIsInits());
	}
	public void setBaseIsInit(Color value, int idx) {
		setRegiIsInits(value, idx, baseRegisters.getIsInits());
	}
	public void setAddrMemIsInit(Color value, int idx) {
		setRegiIsInits(value, idx, addrMemSpRegisters.getIsInits());
	}
	public void setStatusIsInit(Color value, int idx) {
		setRegiIsInits(value, idx, statusRegisters.getIsInits());
	}
	private void setRegiIsInits(Color value, int idx, Color[] isInits) {
		if (idx >= isInits.length)
			throw new IllegalArgumentException(
					ModelConsts.indexOutBoundException);
		isInits[idx] = value;
	}
	public String getCuValue() {
		return cuValue;
	}
	public String getAluValue() {
		return aluValue;
	}
	public Vector<MInstruction> getCs() {
		return cs;
	}
	public Vector<DSValue> getDs() {
		return ds;
	}
	public DSValue getUpdateDS() {
		return updateDS;
	}
	public Vector<ActivationRecord> getSs() {
		return ss;
	}
	public Vector<HeapObj> getHs() {
		return hs;
	}
	public DataAlloc getCsAlloc() {
		return csAlloc;
	}
	public DataAlloc getDsAlloc() {
		return dsAlloc;
	}
	public DataAlloc getSsAlloc() {
		return ssAlloc;
	}
	public DataAlloc getHsAlloc() {
		return hsAlloc;
	}
	public int getCsIdx() {
		return csIdx;
	}
	public int getDsIdx() {
		return dsIdx;
	}
	public int getSsIdx() {
		return ssIdx;
	}
	public int getHsIdx() {
		return hsIdx;
	}
	public void setCsAlloc(DataAlloc csAlloc) {
		this.csAlloc = csAlloc;
	}
	public void setDsAlloc(DataAlloc dsAlloc) {
		this.dsAlloc = dsAlloc;
	}
	public void setSsAlloc(DataAlloc ssAlloc) {
		this.ssAlloc = ssAlloc;
	}
	public void setHsAlloc(DataAlloc hsAlloc) {
		this.hsAlloc = hsAlloc;
	}
	public void setGeneralRegisters(RegiValues generalRegisters) {
		this.generalRegisters = generalRegisters;
	}
	public void setBaseRegisters(RegiValues baseRegisters) {
		this.baseRegisters = baseRegisters;
	}
	public void setAddrMemSpRegisters(RegiValues addrMemSpRegisters) {
		this.addrMemSpRegisters = addrMemSpRegisters;
	}
	public void setCuValue(String cuValue) {
		this.cuValue = cuValue;
	}
	public void setAluValue(String aluValue) {
		this.aluValue = aluValue;
	}
	public void setStatusRegisters(RegiValues statusRegisters) {
		this.statusRegisters = statusRegisters;
	}
	public void setCs(Vector<MInstruction> cs) {
		this.cs = cs;
	}
	public void setDs(Vector<DSValue> ds) {
		this.ds = ds;
	}
	public void setUpdateDS(DSValue updateDS) {
		this.updateDS = updateDS;
	}
	public void setSs(Vector<ActivationRecord> ss) {
		this.ss = ss;
	}
	public void setHs(Vector<HeapObj> hs) {
		this.hs = hs;
	}
	public void setCsIdx(int csIdx) {
		this.csIdx = csIdx;
	}
	public void setDsIdx(int dsIdx) {
		this.dsIdx = dsIdx;
	}
	public void setSsIdx(int ssIdx) {
		this.ssIdx = ssIdx;
	}
	public void setHsIdx(int hsIdx) {
		this.hsIdx = hsIdx;
	}
}
