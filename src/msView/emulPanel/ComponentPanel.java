package msView.emulPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import msView.msLabel.LabelPanel;

public abstract class ComponentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected EmulInfo processInfo;
	public ComponentPanel(String labelName, int labelSize) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
		add(new LabelPanel(labelName, labelSize), BorderLayout.NORTH);
	}
	public abstract void updateView(EmulInfo processInfo);
}
