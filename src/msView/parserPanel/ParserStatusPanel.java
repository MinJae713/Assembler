package msView.parserPanel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;

import dto.assembler.ParsingInfo;
import msConstants.ModelConsts;
import msConstants.ViewConsts;
import msController.assembler.AssembleController;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.InstAdder;
import msView.tableTemplate.strategy.addRows.SymbolAdder;
import msView.tableTemplate.strategy.removeRow.IdentifierSymbolRemover;
import msView.tableTemplate.strategy.setRow.SymbolSetter;
import status.EParsingStatus;

public class ParserStatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private NorthPanel northPanel;
	private SouthPanel southPanel;
	
	public ParserStatusPanel(AssembleController controller) {
		northPanel = new NorthPanel();
		southPanel = new SouthPanel();
		setLayout(new GridLayout(2, 1));
		add(northPanel);
		add(southPanel);
	}
	public void updateView(ParsingInfo parsingInfo) {
		northPanel.updateView(parsingInfo);
		southPanel.updateView(parsingInfo);
	}
	public void initialize(Vector<String> tokenVector) {
		northPanel.initialize(tokenVector);
		southPanel.initialize();
	}
	private class SouthPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private TablePanelTemplate symbolTablePanel;
		private TablePanelTemplate instTablePanel;
		public SouthPanel() {
			symbolTablePanel = new TablePanelTemplate(
					ViewConsts.Parser.South.symbolTableTitle,
					ViewConsts.Table.symbolColumns,
					ViewConsts.Table.labelSize, 
					ViewConsts.Table.parserDim);
			instTablePanel = new TablePanelTemplate(
					ViewConsts.Parser.South.csTableTitle,
					ViewConsts.Table.instColumns,
					ViewConsts.Table.labelSize, 
					ViewConsts.Table.parserDim);
			setLayout(new GridLayout(1, 2));
			JPanel symbolPanel = new JPanel();
			JPanel instPanel = new JPanel();
			symbolPanel.setLayout(new GridBagLayout());
			instPanel.setLayout(new GridBagLayout());
			symbolPanel.add(symbolTablePanel);
			instPanel.add(instTablePanel);
			add(symbolPanel);
			add(instPanel);
		}
		public void updateView(ParsingInfo parsingInfo) {
			symbolTablePanel.clearSelection();
			instTablePanel.clearSelection();
			if (parsingInfo.isNewSymbol())
				symbolTablePanel.addRow(new SymbolAdder(
						parsingInfo.getSymbol(), symbolTablePanel));
			else if (parsingInfo.getUpdateSymbolIdx() != ModelConsts.noUpdate) {
				symbolTablePanel.setRow(new SymbolSetter(
						parsingInfo.getSymbol(), parsingInfo.getUpdateSymbolIdx(), 
						symbolTablePanel), ViewConsts.Parser.South.symbolMaxCount);
			}
			if (parsingInfo.isRemoveIdentifier()) 
				symbolTablePanel.removeRow(new IdentifierSymbolRemover(symbolTablePanel));
			if (parsingInfo.getStatus() == EParsingStatus.eCode) 
				instTablePanel.addRow(new InstAdder(
						parsingInfo.getInstruction(), instTablePanel));
				
		}
		public void initialize() {
			symbolTablePanel.initialize();
			instTablePanel.initialize();
		}
	}
}
