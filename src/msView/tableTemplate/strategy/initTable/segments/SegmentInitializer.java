package msView.tableTemplate.strategy.initTable.segments;

import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.initTable.TableInitializer;

public abstract class SegmentInitializer extends TableInitializer {
	protected int addressIdx;
	protected DataAlloc alloc;
	public SegmentInitializer(TablePanelTemplate template, 
			DataAlloc alloc) {
		super(template);
		this.alloc = alloc;
		addressIdx = alloc.getOffset();
	}
	@Override
	public void initialize() {
		super.initialize();
		template.setAlignSize(alloc.getSize(), true);
	}
}
