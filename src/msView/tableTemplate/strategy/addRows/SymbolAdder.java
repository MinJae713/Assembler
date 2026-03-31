package msView.tableTemplate.strategy.addRows;

import java.util.Vector;

import msModel.assembler.symbolTable.Symbol;
import msView.tableTemplate.TablePanelTemplate;

public class SymbolAdder extends RowAdder {
	private Symbol symbol;
	public SymbolAdder(Symbol symbol, 
			TablePanelTemplate template) {
		super(template);
		this.symbol = symbol;
	}
	@Override
	public void addRow() {
		Vector<String> row = new Vector<String>();
		row.add(symbol.getName());
		row.add(symbol.getSymbolType().name());
		row.add(symbol.getSize()+"");
		row.add(symbol.getOffset()+"");
		template.getTableModel().addRow(row);
		template.setAlignSize(
				template.getAlignSize()+symbol.getSize(),
				symbol.getSize() == 0);
	}

}
