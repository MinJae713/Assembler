package msView.emulPanel.cpu.registerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.register.OneRegisterPanel;
import regiEnums.EAddrMemoryRegisters;

public class AddrMemorySPPanel extends RegisterPanel{
	private static final long serialVersionUID = 1L;
	public AddrMemorySPPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		EAddrMemoryRegisters[] registers = EAddrMemoryRegisters.values();
		JPanel spPanel = getSpPanel(registers);
		JPanel addrMemoryPanel = getAddrMemoryPanel(registers);
		add(spPanel, BorderLayout.NORTH);
		add(addrMemoryPanel, BorderLayout.CENTER);
	}
	private JPanel getAddrMemoryPanel(EAddrMemoryRegisters[] registers) {
		JPanel addrMemoryPanel = new JPanel();
		addrMemoryPanel.setLayout(new GridLayout(2, 2));
		for (int i = 1; i < registers.length; i++) {
			OneRegisterPanel register = 
					new OneRegisterPanel(registers[i].name(), 
							ViewConsts.Emul.Register.initValue);
			register.setFontSize(ViewConsts.Emul.Register.AM.regiFontSize);
			register.setLayout(new GridBagLayout());
			regiPanels.add(register);
			addrMemoryPanel.add(register);
		}
		return addrMemoryPanel;
	}
	private JPanel getSpPanel(EAddrMemoryRegisters[] registers) {
		JPanel spPanel = new JPanel();
		spPanel.setLayout(new GridBagLayout());
		spPanel.setPreferredSize(ViewConsts.Emul.Register.SP.panelDim);
		OneRegisterPanel spRegister = new OneRegisterPanel(
				registers[0].name(), ViewConsts.Emul.Register.initValue);
		spRegister.setPreferredSize(ViewConsts.Emul.Register.SP.regiDim);
		spRegister.setFontSize(ViewConsts.Emul.Register.SP.regiFontSize);
		regiPanels.add(spRegister);
		spPanel.add(spRegister);
		return spPanel;
	}
	@Override
	public void initialize(EmulInfo emulInfo) {
		super.initialize();
		RegiValues addrMemValues = emulInfo.getAddrMemSpRegisters();
		int[] values = addrMemValues.getRegiValues();
		Color[] isInits = addrMemValues.getIsInits();
		int idx = EAddrMemoryRegisters.SP.ordinal();
		regiPanels.get(idx).setValue(values[idx], isInits[idx]);
	}
}
