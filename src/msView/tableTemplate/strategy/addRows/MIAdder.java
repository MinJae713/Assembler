package msView.tableTemplate.strategy.addRows;

import java.util.List;
import java.util.Vector;

import dto.common.mI.MInstruction;
import msView.tableTemplate.TablePanelTemplate;

public class MIAdder extends RowAdder {
	private List<MInstruction> mInstructions;
	public MIAdder(List<MInstruction> mInstructions, 
			TablePanelTemplate template) {
		super(template);
		this.mInstructions = mInstructions;
	}
	@Override
	public void addRow() {
		for (MInstruction mI : mInstructions) {
			Vector<String> row = new Vector<String>();
			row.add(mI.getInstType()+"");
			row.add(mI.getOp1());
			row.add(mI.getOp2());
			row.add(mI.toHex()+"");
			row.add(mI.toBinary()+"");
			template.getTableModel().addRow(row);
		}
		template.setAlignSize(
				template.getTableModel().getRowCount()*4,
				false);
	}
	public int getRowCount() {
		return mInstructions.size();
	}
}
