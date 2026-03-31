package msView.codePanel.textPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class SourcePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	public SourcePanel(String labelText, boolean editable, Dimension dimension) {
		textArea = new JTextArea();
		setLayout(new BorderLayout());
		JLabel label = new JLabel(labelText);
		label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		textArea = new JTextArea();
		textArea.setEditable(editable);
		textArea.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(dimension);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(label, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}
	public String getCode() {
		return textArea.getText();
	}
	public void setCode(String sourceCode) {
		textArea.setText(sourceCode);
	}
}
