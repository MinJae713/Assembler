package msView.tableTemplate.strategy.initTable.segments;

import java.util.Vector;

import dto.common.ActivationRecord;
import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;

public class SSInitializer extends SegmentInitializer {
	private Vector<ActivationRecord> actRecords;
	public SSInitializer(
			TablePanelTemplate template,
			Vector<ActivationRecord> actRecords, 
			DataAlloc alloc) {
		super(template, alloc);
		this.actRecords = actRecords;
	}
	@Override
	public void initialize() {
		super.initialize();
		for (ActivationRecord actRecord : actRecords) {
			Vector<String> row = new Vector<String>();
			row.add(addressIdx+"");
			row.add(actRecord.getFuntionName());
			row.add(actRecord.getRecordType().ordinal()+"");
			row.add(actRecord.getSize()+"");
			template.getTableModel().addRow(row);
			addressIdx += actRecord.getSize();
		}
	}
}
