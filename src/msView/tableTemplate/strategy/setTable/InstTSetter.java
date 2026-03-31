package msView.tableTemplate.strategy.setTable;

import java.util.List;

import msModel.assembler.node.segments.Instruction;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.InstAdder;

public class InstTSetter extends TableSetter {
	private List<Instruction> instructions;
	public InstTSetter(
			TablePanelTemplate template,
			List<Instruction> instructions) {
		super(template);
		this.instructions = instructions;
	}
	@Override
	public void setTable() {
		int alignSize = 0;
		for (Instruction instruction : instructions) {
			template.addRow(new InstAdder(instruction, template));
			alignSize += 4;
		}
		template.setAlignSize(alignSize, false);
	}
}
