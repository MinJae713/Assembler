package msView.tableTemplate.strategy.initTable;

import java.util.List;

import dto.common.mI.MInstruction;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.setTable.MITSetter;

public class MIInitializer extends TableInitializer {
	private List<MInstruction> mI;
	public MIInitializer(
			TablePanelTemplate template,
			List<MInstruction> mI) {
		super(template);
		this.mI = mI;
	}

	@Override
	public void initialize() {
		super.initialize();
		template.setTable(new MITSetter(template, mI));
	}
}
