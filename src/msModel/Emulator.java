package msModel;

import dto.emulator.EmulInfo;
import dto.emulator.ProcessControlBlock;
import msModel.emulator.Bus;
import msModel.emulator.CPU;
import msModel.emulator.Memory;
import msObserver.FinishProcessObserver;

public class Emulator {
	private Bus bus;
	private CPU cpu;
	private Memory memory;
	
	public Emulator() {
		this.bus = new Bus();
		this.cpu = new CPU();
		this.memory = new Memory();
		
		this.cpu.associate(this.bus);
		this.bus.associate(this.memory);
	}
	public void initialize() {
		cpu.initialize();
		bus.initialize();
		memory.initialize();
	}
	public EmulInfo nextProcess() {
		return cpu.nextProcess();
	}
	public EmulInfo nextEmulate() {
		return cpu.nextEmulate();
	}
	public int getMaxProcessCount() {
		return cpu.getMaxProcessCount();
	}
	public ProcessControlBlock getPCB() {
		return memory.getPCB();
	}
	public void setPCB(ProcessControlBlock pcb) {
		cpu.initBaseSPRegister(pcb.getHeaderSegment());
		memory.setPCB(pcb);
	}
	public void setFinishObserver(FinishProcessObserver observer) {
		cpu.setFinishObserver(observer);
	}
}
