package msView.tableTemplate.strategy.setTable;

import java.util.List;

import dto.common.mI.MInstruction;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.MIAdder;

public class MITSetter extends TableSetter {
	private List<MInstruction> mInstructions;
	public MITSetter(
			TablePanelTemplate template,
			List<MInstruction> mInstructions) {
		super(template);
		this.mInstructions = mInstructions;
	}
	@Override
	public void setTable() {
		template.addRow(new MIAdder(mInstructions, template));
		template.setAlignSize(template.getTableModel().getRowCount(), false);
	}
}
