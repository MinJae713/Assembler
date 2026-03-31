package msView.tableTemplate.strategy.setTable;

import java.util.List;

import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.DataAllocAdder;

public class DataAllocTSetter extends TableSetter {
	private List<DataAlloc> allocs;
	public DataAllocTSetter(
			TablePanelTemplate template,
			List<DataAlloc> allocs) {
		super(template);
		this.allocs = allocs;
	}
	@Override
	public void setTable() {
		int alignSize = 0;
		for (DataAlloc alloc : allocs) {
			template.addRow(new DataAllocAdder(alloc, template));
			alignSize += alloc.getSize();
		}
		template.setAlignSize(alignSize, false);
	}
}
