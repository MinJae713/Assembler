package msView.tableTemplate.strategy.addRows;

import java.util.Vector;

import msModel.assembler.node.segments.Instruction;
import msView.tableTemplate.TablePanelTemplate;

public class InstAdder extends RowAdder {
	private Instruction instruction;
	public InstAdder(Instruction instruction, 
			TablePanelTemplate template) {
		super(template);
		this.instruction = instruction;
	}
	@Override
	public void addRow() {
		Vector<String> row = new Vector<String>();
		row.add(instruction.getOffset()+"");
		row.add(instruction.getECommand().getText().toUpperCase());
		row.add(instruction.getOperand()[0]);
		row.add(instruction.getOperand()[1]);
		template.getTableModel().addRow(row);
		int alignSize = template.getAlignSize();
		template.setAlignSize(alignSize += 4, false);
	}

}
