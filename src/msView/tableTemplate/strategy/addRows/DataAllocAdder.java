package msView.tableTemplate.strategy.addRows;

import java.util.Vector;

import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;

public class DataAllocAdder extends RowAdder {
	private DataAlloc alloc;
	public DataAllocAdder(DataAlloc alloc, 
			TablePanelTemplate template) {
		super(template);
		this.alloc = alloc;
	}
	@Override
	public void addRow() {
		Vector<String> row = new Vector<String>();
		row.add(alloc.getName());
		row.add(alloc.getOffset()+"");
		row.add(alloc.getSize()+"");
		template.getTableModel().addRow(row);
		template.setAlignSize(
				template.getAlignSize()+alloc.getSize(),
				false);
	}

}
