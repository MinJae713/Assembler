package msView.tableTemplate.strategy.initTable.segments;

import java.util.Vector;

import dto.common.HeapObj;
import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;

public class HSInitializer extends SegmentInitializer {
	private Vector<HeapObj> heapObjs;
	public HSInitializer(
			TablePanelTemplate template,
			Vector<HeapObj> heapObjs, 
			DataAlloc alloc) {
		super(template, alloc);
		this.heapObjs = heapObjs;
	}

	@Override
	public void initialize() {
		super.initialize();
		for (HeapObj heapObj : heapObjs) {
			Vector<String> row = new Vector<String>();
			row.add(addressIdx+"");
			row.add(heapObj.getTypeName());
			row.add(heapObj.getSize()+"");
			template.getTableModel().addRow(row);
			addressIdx -= heapObj.getSize();
		}
	}
}
