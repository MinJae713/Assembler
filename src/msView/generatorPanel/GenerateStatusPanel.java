package msView.generatorPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.assembler.GenerateInfo;
import msConstants.ViewConsts;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;

public class GenerateStatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private InstLabel curInstLabel;
	private InstLabel nextInstLabel;
	private GenerateContentPanel contentPanel;
	public GenerateStatusPanel() {
		setLayout(new BorderLayout());
		curInstLabel = new InstLabel(ViewConsts.Generator.CurInst.labelName);
		nextInstLabel = new InstLabel(ViewConsts.Generator.NextInst.labelName);
		contentPanel = new GenerateContentPanel();
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 2));
		labelPanel.add(curInstLabel);
		labelPanel.add(nextInstLabel);
		add(labelPanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}
	public void updateView(GenerateInfo generateInfo) {
		Vector<Instruction> instructions = contentPanel.getInstructions();
		curInstLabel.updateView(generateInfo.getCurInst());
		nextInstLabel.updateView(generateInfo.getNextInst(instructions));
		contentPanel.updateView(generateInfo);
	}
	public void initialize(Vector<Symbol> symbols, 
			Vector<Instruction> instructions) {
		curInstLabel.initialize();
		nextInstLabel.initialize();
		contentPanel.initialize(symbols, instructions);
	}
	private class InstLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		private String title;
		public InstLabel(String title) {
			this.title = title;
			setHorizontalAlignment(JLabel.LEFT);
			setFont(new Font(getFont().getName(), Font.BOLD, 
					ViewConsts.Generator.CurNextCommon.labelSize));
			initialize();
		}
		public void updateView(Instruction instruction) {
			String instInfo = instruction == null ? 
					ViewConsts.Generator.NextInst.no : 
					instruction.toString();
			setText(title+instInfo);
		}
		public void initialize() {
			setText(title);
		}
	}
}
