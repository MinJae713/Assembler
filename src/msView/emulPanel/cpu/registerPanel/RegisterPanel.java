package msView.emulPanel.cpu.registerPanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.register.OneRegisterPanel;

public abstract class RegisterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected List<OneRegisterPanel> regiPanels;
	public RegisterPanel() {
		regiPanels = new ArrayList<OneRegisterPanel>();
	}
	public void updateView(RegiValues regiValues) {
		int[] registers = regiValues.getRegiValues();
		Color[] isInits = regiValues.getIsInits();
		for (int i = 0; i < regiValues.getSize(); i++) 
			regiPanels.get(i).setValue(registers[i], isInits[i]);
	}
	public void initialize() {
		for (OneRegisterPanel registerPanel : regiPanels)
			registerPanel.setValue(ViewConsts.Emul.Register.initValue, Color.BLACK);
	}
	public void initialize(EmulInfo emulInfo) {
		initialize();
	}
}
