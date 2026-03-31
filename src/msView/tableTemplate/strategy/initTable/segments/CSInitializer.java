package msView.tableTemplate.strategy.initTable.segments;

import java.util.Vector;

import dto.common.mI.MInstruction;
import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;

public class CSInitializer extends SegmentInitializer {
	private Vector<MInstruction> instructions;
	public CSInitializer(
			TablePanelTemplate template,
			Vector<MInstruction> instructions, 
			DataAlloc alloc) {
		super(template, alloc);
		this.instructions = instructions;
	}
	@Override
	public void initialize() {
		super.initialize();
		for (MInstruction instruction : instructions) {
			Vector<String> row = new Vector<String>();
			row.add(addressIdx+"");
			row.add(instruction.getOpCode(
					instruction.getInstType(),true));
			row.add(instruction.getOp1Hex());
			row.add(instruction.getOp2Hex());
			template.getTableModel().addRow(row);
			addressIdx += 4;
		}
	}
}
