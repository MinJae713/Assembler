package msView.tableTemplate.strategy.initTable;

import java.util.List;

import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.setTable.DataAllocTSetter;

public class DataAllocInitializer extends TableInitializer {
	private List<DataAlloc> allocs;
	public DataAllocInitializer(
			TablePanelTemplate template,
			List<DataAlloc> allocs) {
		super(template);
		this.allocs = allocs;
	}
	@Override
	public void initialize() {
		super.initialize();
		template.setTable(new DataAllocTSetter(template, allocs));
	}
}
