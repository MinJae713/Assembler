package msView.emulPanel.cpu;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import msConstants.ViewConsts;
import msView.emulPanel.ComponentPanel;
import msView.emulPanel.cpu.etc.CPUCenterPanel;
import msView.emulPanel.cpu.registerPanel.GeneralPanel;
import msView.emulPanel.cpu.registerPanel.RegisterPanel;

public class CPUPanel extends ComponentPanel {
	private static final long serialVersionUID = 1L;
	private ProcessingPanel processingPanel;
	public CPUPanel() {
		super(ViewConsts.Emul.CPU.labelName, 
				ViewConsts.Emul.CPU.labelSize);
		processingPanel = new ProcessingPanel();
		add(processingPanel, BorderLayout.CENTER);
	}
	public void initialize(EmulInfo emulInfo) {
		processingPanel.initialize(emulInfo);
	}
	@Override
	public void updateView(EmulInfo emulInfo) {
		processingPanel.updateView(emulInfo);
	}
	private class ProcessingPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private RegisterPanel generalPanel;
		private CPUCenterPanel cpuCenterPanel;
		public ProcessingPanel() {
			setLayout(new BorderLayout());
			generalPanel = new GeneralPanel();
			cpuCenterPanel = new CPUCenterPanel();
			add(generalPanel, BorderLayout.NORTH);
			add(cpuCenterPanel, BorderLayout.CENTER);
		}
		public void initialize(EmulInfo emulInfo) {
			generalPanel.initialize();
			cpuCenterPanel.initialize(emulInfo);
		}
		public void updateView(EmulInfo emulInfo) {
			generalPanel.updateView(emulInfo.getGeneralRegisters());
			cpuCenterPanel.updateView(emulInfo.getBaseRegisters(), 
					emulInfo.getAddrMemSpRegisters(), emulInfo.getCuValue(), 
					emulInfo.getAluValue(), emulInfo.getStatusRegisters());
		}
	}
}
