package msView.tableTemplate.strategy.setRow;

import msView.tableTemplate.TablePanelTemplate;

public abstract class RowSetter {
	protected TablePanelTemplate template;
	protected int idx;
	public RowSetter(TablePanelTemplate template, int idx) {
		this.template = template;
		this.idx = idx;
	}
	public abstract void setRow();
	public int getIdx() {
		return idx;
	}
}
