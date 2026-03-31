package msView.tableTemplate.strategy.addRows;

import msView.tableTemplate.TablePanelTemplate;

public abstract class RowAdder {
	protected TablePanelTemplate template;
	public RowAdder(TablePanelTemplate template) {
		this.template = template;
	}
	public abstract void addRow();
}
