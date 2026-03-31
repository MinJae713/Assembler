package msView.emulPanel.cpu.etc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import dto.emulator.RegiValues;
import msConstants.ViewConsts;
import msView.emulPanel.cpu.registerPanel.AddrMemorySPPanel;
import msView.emulPanel.cpu.registerPanel.BasePanel;
import msView.emulPanel.cpu.registerPanel.RegisterPanel;
import msView.emulPanel.cpu.registerPanel.StatusPanel;
import msView.msLabel.LabelPanel;

public class CPUCenterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private RegisterPanel basePanel;
	private RegisterPanel addrMemorySPPanel;
	private CUALUPanel cualuPanel;
	private RegisterPanel statusPanel;
	public CPUCenterPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridLayout(1, 2));
		add(new WestPanel());
		add(new EastPanel());
	}
	public void initialize(EmulInfo emulInfo) {
		basePanel.initialize(emulInfo);
		addrMemorySPPanel.initialize(emulInfo);
		cualuPanel.initialize();
		statusPanel.initialize();
	}
	private class WestPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public WestPanel() {
			addrMemorySPPanel = new AddrMemorySPPanel();
			setLayout(new BorderLayout());
			add(new BaseSidePanel(), BorderLayout.WEST);
			add(addrMemorySPPanel, BorderLayout.CENTER);
		}
	}
	private class BaseSidePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public BaseSidePanel() {
			basePanel = new BasePanel();
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.black));
			setPreferredSize(ViewConsts.Emul.Register.Base.sideDim);
			add(new LabelPanel(ViewConsts.Emul.Register.Base.labelName, 
					ViewConsts.Emul.Register.Base.labelSize), BorderLayout.NORTH);
			add(basePanel, BorderLayout.CENTER);
		}
	}
	private class EastPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public EastPanel() {
			cualuPanel = new CUALUPanel();
			setLayout(new BorderLayout());
			add(cualuPanel, BorderLayout.CENTER);
			add(new StatusSidePanel(), BorderLayout.EAST);
		}
	}
	private class StatusSidePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public StatusSidePanel() {
			statusPanel = new StatusPanel();
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.black));
			setPreferredSize(ViewConsts.Emul.Register.Status.sideDim);
			add(new LabelPanel(ViewConsts.Emul.Register.Status.labelName, 
					ViewConsts.Emul.Register.Status.labelSize), BorderLayout.NORTH);
			add(statusPanel, BorderLayout.CENTER);
		}
	}
	public void updateView(RegiValues baseRegiValues, 
			RegiValues addrMemSpRegiValues, String cuValue, 
			String aluValue, RegiValues statusRegiValues) {
		basePanel.updateView(baseRegiValues);
		addrMemorySPPanel.updateView(addrMemSpRegiValues);
		cualuPanel.updateView(cuValue, aluValue);
		statusPanel.updateView(statusRegiValues);
	}
}
