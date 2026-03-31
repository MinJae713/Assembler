package msView.emulPanel.cpu.etc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import msConstants.ViewConsts;
import msView.msLabel.LabelPanel;

public class CUALUPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ResultPanel cuPanel;
	private ResultPanel aluPanel;
	public CUALUPanel() {
		setLayout(new GridLayout(1, 2));
		setBorder(BorderFactory.createLineBorder(Color.black));
		cuPanel = new ResultPanel("CU");
		aluPanel = new ResultPanel("ALU");
		add(cuPanel);
		add(aluPanel);
	}
	public void initialize() {
		cuPanel.initialize();
		aluPanel.initialize();
	}
	public void updateView(String cuValue, String aluValue) {
		cuPanel.updateView(cuValue);
		aluPanel.updateView(aluValue);
	}
	private class ResultPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private JTextArea textArea;
		public ResultPanel(String labelName) {
			setLayout(new GridBagLayout());
			JPanel panel = new JPanel();
			panel.setPreferredSize(ViewConsts.Emul.Register.CUALU.dim);
			panel.setLayout(new BorderLayout());
			panel.add(new LabelPanel(labelName, 
					ViewConsts.Emul.Register.CUALU.labelSize), BorderLayout.NORTH);
			textArea = new JTextArea();
			textArea.setEditable(ViewConsts.Emul.Register.CUALU.editable);
			textArea.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(textArea, BorderLayout.CENTER);
			add(panel);
		}
		public void initialize() {
			textArea.setText("");
		}
		public void updateView(String computeValue) {
			textArea.setText(computeValue);
		}
	}
}
