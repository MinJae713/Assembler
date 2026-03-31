package msView.emulPanel.cpu.registerPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.register.OneRegisterPanel;
import regiEnums.EBaseRegisters;

public class BasePanel extends RegisterPanel {
	private static final long serialVersionUID = 1L;
	public BasePanel() {
		EBaseRegisters[] registers = EBaseRegisters.values();
		setLayout(new GridLayout(EBaseRegisters.values().length, 1));
		for (int i = 0; i < registers.length; i++) {
			JPanel basePanel = new JPanel();
			OneRegisterPanel register = 
					new OneRegisterPanel(registers[i].name(), 
							ViewConsts.Emul.Register.initValue);
			register.setPreferredSize(ViewConsts.Emul.Register.Base.regisDim);
			register.setLayout(new FlowLayout(FlowLayout.LEADING));
			register.setFontSize(ViewConsts.Emul.Register.Base.regiFontSize);
			basePanel.add(register);
			regiPanels.add(register);
			add(basePanel);
		}
	}
	@Override
	public void updateView(RegiValues regiValues) {
		Color[] isInits = regiValues.getIsInits();
		for (int i = 0; i < regiValues.getSize(); i++) 
			regiPanels.get(i).setValue(isInits[i]);
	}
	@Override
	public void initialize(EmulInfo emulInfo) {
		super.initialize(emulInfo);
		RegiValues baseValues = emulInfo.getBaseRegisters();
		int[] values = baseValues.getRegiValues();
		Color[] isInits = baseValues.getIsInits();
		int size = baseValues.getSize();
		for (int i=0; i<size; i++) 
			regiPanels.get(i).setValue(values[i], isInits[i]);
	}
}
