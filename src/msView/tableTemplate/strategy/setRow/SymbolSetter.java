package msView.tableTemplate.strategy.setRow;

import javax.swing.table.DefaultTableModel;

import msModel.assembler.symbolTable.Symbol;
import msView.tableTemplate.TablePanelTemplate;

public class SymbolSetter extends RowSetter {
	private Symbol symbol;
	public SymbolSetter(Symbol symbol, int idx,
			TablePanelTemplate template) {
		super(template, idx);
		this.symbol = symbol;
	}

	@Override
	public void setRow() {
		DefaultTableModel model = template.getTableModel();
		model.setValueAt(symbol.getSymbolType().name(), idx, 1);
		model.setValueAt(symbol.getSize(), idx, 2);
		model.setValueAt(symbol.getOffset(), idx, 3);
		template.setAlignSize(template.getAlignSize()+
				symbol.getSize(), false);
	}
}
