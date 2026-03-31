package msController.emulator;

import dto.emulator.EmulInfo;
import dto.emulator.ProcessControlBlock;
import msModel.Emulator;
import msObserver.FinishProcessObserver;

public class EmulControllerImpl implements EmulController {
	private Emulator emulator;
	public EmulControllerImpl() {
		emulator = new Emulator();
	}
	@Override
	public ProcessControlBlock getPCB() {
		return emulator.getPCB();
	}
	@Override
	public void setPCB(ProcessControlBlock pcb) {
		emulator.setPCB(pcb);
	}
	@Override
	public EmulInfo nextEmulate() {
		return emulator.nextEmulate();
	}
	@Override
	public void initEmulator() {
		emulator.initialize();
	}
	@Override
	public EmulInfo nextProcess() {
		return emulator.nextProcess();
	}
	@Override
	public int getMaxProcessCount() {
		return emulator.getMaxProcessCount();
	}
	@Override
	public void setFinishObserver(FinishProcessObserver observer) {
		emulator.setFinishObserver(observer);
	}
}
