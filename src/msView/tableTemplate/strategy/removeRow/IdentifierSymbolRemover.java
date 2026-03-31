package msView.tableTemplate.strategy.removeRow;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import msModel.assembler.symbolTable.ESymbolType;
import msView.tableTemplate.TablePanelTemplate;

public class IdentifierSymbolRemover extends RowRemover {
	public IdentifierSymbolRemover(TablePanelTemplate template) {
		super(template);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void removeRow() {
		DefaultTableModel model = template.getTableModel();
		Vector<Vector> symbols = model.getDataVector();
		int[] idxs = new int[0];
		for (int i=0; i<symbols.size(); i++) {
			Vector symbol = symbols.get(i);
			for (int j=0; j<symbol.size(); j++)
				if (symbol.get(j).equals(ESymbolType.eIdentifier.name())) {
					idxs = Arrays.copyOf(idxs, idxs.length+1);
					idxs[idxs.length-1] = i;
				} else if (symbol.get(j).equals(ESymbolType.eLabel.name())) {
					// : 제거
					String label = (String)symbol.get(0);
					label = label.substring(0, label.length()-1);
					model.setValueAt(label, i, 0);
				}
		}
		for (int i=idxs.length-1; i>=0; i--) model.removeRow(idxs[i]);
	}
}
