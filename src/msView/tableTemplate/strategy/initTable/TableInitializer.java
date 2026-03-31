package msView.tableTemplate.strategy.initTable;

import msView.tableTemplate.TablePanelTemplate;

public abstract class TableInitializer {
	protected TablePanelTemplate template;
	public TableInitializer(TablePanelTemplate template) {
		this.template = template;
	}
	public void initialize() {
		template.initialize();
	}
}
