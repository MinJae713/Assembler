package msView.tableTemplate.strategy.setTable;

import java.util.List;

import msModel.assembler.symbolTable.Symbol;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.SymbolAdder;

public class SymbolTSetter extends TableSetter {
	private List<Symbol> symbols;
	public SymbolTSetter(
			TablePanelTemplate template,
			List<Symbol> symbols) {
		super(template);
		this.symbols = symbols;
	}
	@Override
	public void setTable() {
		int alignSize = 0;
		for (Symbol symbol : symbols) {
			template.addRow(new SymbolAdder(symbol, template));
			alignSize += symbol.getSize();
		}
		template.setAlignSize(alignSize, false);
	}
}
