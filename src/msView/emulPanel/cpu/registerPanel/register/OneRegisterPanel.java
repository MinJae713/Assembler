package msView.emulPanel.cpu.registerPanel.register;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import msConstants.ModelConsts;

public class OneRegisterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private String regiName;
	private JLabel titleLabel;
	private OneRegisterValue valueLabel;
	public OneRegisterPanel(String regiName, int value) {
		this.regiName = regiName;
		valueLabel = new OneRegisterValue(value);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
		titleLabel = new JLabel(regiName+" : ");
		add(titleLabel);
		add(valueLabel);
	}
	private class OneRegisterValue extends JLabel {
		private static final long serialVersionUID = 1L;
		private int value;
		public OneRegisterValue(int value) {
			this.value = value;
			setVerticalAlignment(JLabel.CENTER);
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value, Color isInit) {
			this.value = value;
			value = value == ModelConsts.clearSelection ? 0 : value;
			setText(value+"");
			setValue(isInit);
		}
		public void setValue(Color isInit) {
			setForeground(isInit);
		}
	}
	public void setFontSize(int size) {
		Font font = valueLabel.getFont();
		Font newFont = new Font(font.getName(), font.getStyle(), size);
		titleLabel.setFont(newFont);
		valueLabel.setFont(newFont);
	}
	public void setRegiBorder(Border border) {
		setBorder(border);
	}
	public String getRegiName() {
		return regiName;
	}
	public int getValue() {
		return valueLabel.getValue();
	}
	public void setValue(int value, Color isInit) {
		valueLabel.setValue(value, isInit);
	}
	public void setValue(Color isInit) {
		valueLabel.setValue(isInit);
	}
}
