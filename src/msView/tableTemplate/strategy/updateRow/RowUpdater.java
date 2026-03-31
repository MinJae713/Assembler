package msView.tableTemplate.strategy.updateRow;

import msView.tableTemplate.TablePanelTemplate;

public abstract class RowUpdater {
	protected TablePanelTemplate template;
	protected int idx;
	public RowUpdater(TablePanelTemplate template, int idx) {
		this.template = template;
		this.idx = idx;
	}
	public abstract void updateRow();
}
