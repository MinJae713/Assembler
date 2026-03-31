package msView.tableTemplate.strategy.initTable;

import java.util.List;

import msModel.assembler.node.segments.Instruction;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.setTable.InstTSetter;

public class InstInitializer extends TableInitializer {
	private List<Instruction> instructions;
	public InstInitializer(
			TablePanelTemplate template,
			List<Instruction> instructions) {
		super(template);
		this.instructions = instructions;
	}

	@Override
	public void initialize() {
		super.initialize();
		template.setTable(new InstTSetter(template, instructions));
	}

}
