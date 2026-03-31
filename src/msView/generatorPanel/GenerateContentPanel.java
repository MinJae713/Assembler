package msView.generatorPanel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

import dto.assembler.GenerateInfo;
import msConstants.ModelConsts;
import msConstants.ViewConsts;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.MIAdder;
import msView.tableTemplate.strategy.initTable.InstInitializer;
import msView.tableTemplate.strategy.initTable.SymbolInitializer;

public class GenerateContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Vector<Instruction> instructions; 
	private TablePanelTemplate instTablePanel;
	private TablePanelTemplate symbolTablePanel;
	private TablePanelTemplate miTablePanel;
	public GenerateContentPanel() {
		instTablePanel = new TablePanelTemplate(
				ViewConsts.Generator.InstTable.labelName, 
				ViewConsts.Table.instColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Generator.InstTable.tableDim);
		symbolTablePanel = new TablePanelTemplate(
				ViewConsts.Generator.SymbolTable.labelName, 
				ViewConsts.Table.symbolColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Generator.SymbolTable.tableDim);
		miTablePanel = new TablePanelTemplate(
				ViewConsts.Generator.MITable.labelName, 
				ViewConsts.Table.mIColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Generator.MITable.tableDim);
		
		setLayout(new GridLayout(1, 2));
		add(new WestPanel());
		add(new EastPanel());
	}
	public Vector<Instruction> getInstructions() {
		return instructions;
	}
	public void updateView(GenerateInfo generateInfo) {
		instTablePanel.updateView(generateInfo.getInstIdx(), 
				generateInfo.getInstIdx());
		instTablePanel.scrollOneRow(generateInfo.getInstIdx(), 
				ViewConsts.Generator.maxRowCount);
		int firstIdx = generateInfo.getFirstSymbolIdx();
		int secondIdx = generateInfo.getSecondSymbolIdx();
		if (firstIdx == ModelConsts.clearSelection && 
				secondIdx == ModelConsts.clearSelection)
			symbolTablePanel.clearSelection();
		else if (firstIdx != ModelConsts.clearSelection && 
				secondIdx != ModelConsts.clearSelection) {
			symbolTablePanel.updateView(firstIdx, secondIdx);
			if (Math.abs(secondIdx-firstIdx) > 1) {
				if (secondIdx > firstIdx)
					symbolTablePanel.removeSelection(firstIdx+1, secondIdx-1);
				else symbolTablePanel.removeSelection(secondIdx+1, firstIdx-1);
			}
			if (firstIdx > secondIdx) 
				symbolTablePanel.scrollOneRow(firstIdx, ViewConsts.Generator.maxRowCount);
			else symbolTablePanel.scrollOneRow(secondIdx, ViewConsts.Generator.maxRowCount);
		} else if (firstIdx != ModelConsts.clearSelection) {
			symbolTablePanel.updateView(firstIdx, firstIdx);
			symbolTablePanel.scrollOneRow(firstIdx, ViewConsts.Generator.maxRowCount);
		}
		miTablePanel.addRow(new MIAdder(
				generateInfo.getMInstructions(), miTablePanel));
	}
	public void initialize(Vector<Symbol> symbols, 
			Vector<Instruction> instructions) {
		this.instructions = instructions;
		instTablePanel.initialize(new InstInitializer(instTablePanel, instructions));
		symbolTablePanel.initialize(new SymbolInitializer(symbolTablePanel, symbols));
		miTablePanel.initialize(); 
	}
	private class WestPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public WestPanel() {
			setLayout(new GridLayout(2, 1));
			add(new NorthPanel());
			add(new SouthPanel());
		}
	}
	private class NorthPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public NorthPanel() {
			setLayout(new GridBagLayout());
			add(instTablePanel);
		}
	}
	private class SouthPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public SouthPanel() {
			setLayout(new GridBagLayout());
			add(symbolTablePanel);
		}
	}
	private class EastPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public EastPanel() {
			setLayout(new GridBagLayout());
			add(miTablePanel);
		}
	}
}
