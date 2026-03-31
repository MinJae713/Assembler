package msView.msLabel;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected JLabel label;
	protected String labelName;
	public LabelPanel(String labelName, int labelSize) {
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEADING);
		setLayout(layout);
		this.labelName = labelName;
		label = new JLabel();
		setName();
		Font font = label.getFont();
		label.setFont(new Font(font.getName(), font.getStyle(), labelSize));
		add(label);
	}
	public void setName() {
		label.setText(labelName);
	}
}
