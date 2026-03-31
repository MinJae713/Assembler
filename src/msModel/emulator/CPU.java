package msModel.emulator;

import java.awt.Color;
import java.util.Vector;

import dto.common.mI.MInstruction;
import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ModelConsts;
import msModel.assembler.node.entity.DataAlloc;
import msObserver.FinishProcessObserver;
import regiEnums.EAddrMemoryRegisters;
import regiEnums.EBaseRegisters;
import regiEnums.EGeneralRegisters;
import regiEnums.EStatusRegisters;

public class CPU {
	private int csIdx;
	private EmulInfo curEmul;
	private MInstruction curMInst;
	private int processCount;
	
	private Bus bus;
	private int[] generalRegisters;
	private int[] baseRegisters;
	private int[] addrMemoryRegister;
	private int[] statusRegister;
	private FinishProcessObserver observer;
	public void initialize() {
		csIdx = 0;
		curMInst = null;
		processCount = 0;
		
		generalRegisters = new int[EGeneralRegisters.values().length];
		baseRegisters = new int[EBaseRegisters.values().length];
		addrMemoryRegister = new int[EAddrMemoryRegisters.values().length];
		statusRegister = new int[EStatusRegisters.values().length];
		
		curEmul = new EmulInfo();
		RegiValues nGeneral = initRegisters(generalRegisters.length);
		RegiValues nBase = initRegisters(baseRegisters.length);
		RegiValues nAddrMemSp = initRegisters(addrMemoryRegister.length);
		RegiValues nStatus = initRegisters(statusRegister.length);
		curEmul.setGeneralRegisters(nGeneral);
		curEmul.setBaseRegisters(nBase);
		curEmul.setAddrMemSpRegisters(nAddrMemSp);
		curEmul.setStatusRegisters(nStatus);
	}
	public EmulInfo nextProcess() {
		try {
			int idx = EAddrMemoryRegisters.PC.ordinal();
			int pc = addrMemoryRegister[idx];
			curEmul.setAddrMemIsInit(Color.BLACK, idx);
			curEmul = curMInst.nextProcess(curEmul, 
					processCount, this, pc);
			processCount++;
			if (processCount == getMaxProcessCount()) 
				processCount = 0;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return curEmul;
	}
	public EmulInfo nextEmulate() {
		int idx = EAddrMemoryRegisters.PC.ordinal();
		int pc = addrMemoryRegister[idx];
		curMInst = bus.loadInstruction(pc); 
		curEmul.setCsIdx(csIdx); 
		RegiValues regiValues = curEmul.getAddrMemSpRegisters();
		updateRegiValue(regiValues, idx, pc, Color.RED);
		return curEmul;
	}
	private RegiValues initRegisters(int size) {
		int[] registers = new int[size];
		Color[] isInit = new Color[size];
		for (int i=0; i<size; i++) {
			registers[i] = 0;
			isInit[i] = Color.BLACK;
		}
		return new RegiValues(registers, isInit, size);
	}
	public void associate(Bus bus) {
		this.bus = bus;
	}
	public int getMaxProcessCount() {
		return curMInst.getProcessCount();
	}
	public void initBaseSPRegister(Vector<DataAlloc> hs) {
		for (DataAlloc alloc : hs) {
			if (alloc.getName().equals(ModelConsts.csName))
				baseRegisters[EBaseRegisters.CS.ordinal()] = alloc.getOffset();
			else if (alloc.getName().equals(ModelConsts.dsName))
				baseRegisters[EBaseRegisters.DS.ordinal()] = alloc.getOffset();
			else if (alloc.getName().equals(ModelConsts.ssName)) {
				baseRegisters[EBaseRegisters.SS.ordinal()] = alloc.getOffset();
				addrMemoryRegister[EAddrMemoryRegisters.SP.ordinal()] = alloc.getOffset();
			}
			else if (alloc.getName().equals(ModelConsts.hsName))
				baseRegisters[EBaseRegisters.HS.ordinal()] = alloc.getOffset();
		}
		initSP();
	}
	private void initSP() {
		RegiValues nAddrMemSp = curEmul.getAddrMemSpRegisters();
		int spIdx = EAddrMemoryRegisters.SP.ordinal();
		updateRegiValue(nAddrMemSp, spIdx, addrMemoryRegister[spIdx], Color.BLACK);
	}
	public void updateRegiValue(RegiValues regiValues, 
			int idx, int value, Color isInit) {
		if (idx >= regiValues.getSize())
			throw new IllegalArgumentException(ModelConsts.indexOutBoundException);
		regiValues.setOneRegiValue(idx, value, isInit);
	}
	public int getCsIdx() {
		return csIdx;
	}
	public Bus getBus() {
		return bus;
	}
	public int[] getGeneralRegisters() {
		return generalRegisters;
	}
	public int[] getBaseRegisters() {
		return baseRegisters;
	}
	public int[] getAddrMemoryRegister() {
		return addrMemoryRegister;
	}
	public int[] getStatusRegister() {
		return statusRegister;
	}
	public void setCsIdx(int csIdx) {
		this.csIdx = csIdx;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	public void setGeneralRegisters(int[] generalRegisters) {
		this.generalRegisters = generalRegisters;
	}
	public void setBaseRegisters(int[] baseRegisters) {
		this.baseRegisters = baseRegisters;
	}
	public void setAddrMemoryRegister(int[] addrMemoryRegister) {
		this.addrMemoryRegister = addrMemoryRegister;
	}
	public void setStatusRegister(int[] statusRegister) {
		this.statusRegister = statusRegister;
	}
	public void setRegiValue(int[] register, int idx, int value) 
			throws IllegalAccessException {
		if (idx >= register.length)
			throw new IllegalAccessException(ModelConsts.indexOutBoundException);
		register[idx] = value;
	}
	public FinishProcessObserver getFinishObserver() {
		return observer;
	}
	public void setFinishObserver(FinishProcessObserver observer) {
		this.observer = observer;
	}
}
