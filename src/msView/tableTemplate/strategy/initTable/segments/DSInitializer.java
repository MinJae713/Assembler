package msView.tableTemplate.strategy.initTable.segments;

import java.util.Vector;

import dto.emulator.DSValue;
import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;

public class DSInitializer extends SegmentInitializer {
	private Vector<DSValue> dsValues;
	public DSInitializer(
			TablePanelTemplate template,
			Vector<DSValue> dsValues, 
			DataAlloc alloc) {
		super(template, alloc);
		this.dsValues = dsValues;
	}
	@Override
	public void initialize() {
		super.initialize();
		for (DSValue dsValue : dsValues) {
			Vector<String> row = new Vector<String>();
			DataAlloc dataAlloc = dsValue.getAlloc();
			row.add(addressIdx+"");
			row.add(dataAlloc.getName());
			row.add("");
			row.add(dataAlloc.getSize()+"");
			template.getTableModel().addRow(row);
			addressIdx += dataAlloc.getSize();
		}
	}
}
