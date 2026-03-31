package msView.tableTemplate.strategy.initTable;

import java.util.List;

import msModel.assembler.symbolTable.Symbol;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.setTable.SymbolTSetter;

public class SymbolInitializer extends TableInitializer {
	private List<Symbol> symbols;
	public SymbolInitializer(
			TablePanelTemplate template,
			List<Symbol> symbols) {
		super(template);
		this.symbols = symbols;
	}

	@Override
	public void initialize() {
		super.initialize();
		template.setTable(new SymbolTSetter(template, symbols));
	}

}
