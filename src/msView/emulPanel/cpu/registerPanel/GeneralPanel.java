package msView.emulPanel.cpu.registerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.register.OneRegisterPanel;
import msView.msLabel.LabelPanel;
import regiEnums.EGeneralRegisters;

public class GeneralPanel extends RegisterPanel {
	private static final long serialVersionUID = 1L;
	public GeneralPanel() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		add(new LabelPanel(ViewConsts.Emul.Register.General.labelName, 
				ViewConsts.Emul.Register.General.labelSize), BorderLayout.NORTH);
		add(new GeneralRegisterPanel(), BorderLayout.CENTER);
	}
	private class GeneralRegisterPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		public GeneralRegisterPanel() {
			EGeneralRegisters[] registers = EGeneralRegisters.values();
			FlowLayout layout = new FlowLayout();
			layout.setHgap(ViewConsts.Emul.Register.General.regisGap[0]);
			layout.setVgap(ViewConsts.Emul.Register.General.regisGap[1]);
			setLayout(layout);
			for (int i = 0; i < registers.length; i++) {
				OneRegisterPanel register = 
						new OneRegisterPanel(registers[i].name(), 
								ViewConsts.Emul.Register.initValue);
				register.setLayout(new FlowLayout(FlowLayout.LEADING));
				register.setPreferredSize(ViewConsts.Emul.Register.General.regisDim);
				register.setFontSize(ViewConsts.Emul.Register.General.regiFontSize);
				regiPanels.add(register);
				add(register);
			}
		}
	}
}
