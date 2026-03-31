package msController.emulator;

import dto.emulator.EmulInfo;
import dto.emulator.ProcessControlBlock;
import msObserver.FinishProcessObserver;

public interface EmulController {
	public ProcessControlBlock getPCB();
	public void setPCB(ProcessControlBlock pcb);
	public EmulInfo nextEmulate();
	public void initEmulator();
	public EmulInfo nextProcess();
	public int getMaxProcessCount();
	public void setFinishObserver(FinishProcessObserver observer);
}
