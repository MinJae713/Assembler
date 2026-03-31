package msView.emulPanel.cpu.registerPanel;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.register.OneRegisterPanel;
import regiEnums.EStatusRegisters;

public class StatusPanel extends RegisterPanel {
	private static final long serialVersionUID = 1L;
	public StatusPanel() {
		EStatusRegisters[] registers = EStatusRegisters.values();
		setLayout(new GridLayout(EStatusRegisters.values().length, 1));
		for (int i = 0; i < registers.length; i++) {
			OneRegisterPanel register = 
					new OneRegisterPanel(registers[i].name(), 
							ViewConsts.Emul.Register.initValue);
			register.setPreferredSize(ViewConsts.Emul.Register.Status.regisDim);
			register.setLayout(new FlowLayout(FlowLayout.LEADING));
			register.setFontSize(ViewConsts.Emul.Register.Status.regiFontSize);
			register.setBorder(null);
			regiPanels.add(register);
			add(register);
		}
	}
}
