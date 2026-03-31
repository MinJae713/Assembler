package msView.tableTemplate.strategy.removeRow;

import msView.tableTemplate.TablePanelTemplate;

public abstract class RowRemover {
	protected TablePanelTemplate template;
	public RowRemover(TablePanelTemplate template) {
		this.template = template;
	}
	public abstract void removeRow();
}
