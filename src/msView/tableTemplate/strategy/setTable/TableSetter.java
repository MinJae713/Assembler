package msView.tableTemplate.strategy.setTable;

import msView.tableTemplate.TablePanelTemplate;

public abstract class TableSetter {
	protected TablePanelTemplate template;
	public TableSetter(TablePanelTemplate template) {
		this.template = template;
	}
	public abstract void setTable();
}
