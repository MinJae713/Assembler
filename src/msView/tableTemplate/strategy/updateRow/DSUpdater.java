package msView.tableTemplate.strategy.updateRow;

import java.util.Vector;

import dto.emulator.DSValue;
import msConstants.ModelConsts;
import msView.tableTemplate.TablePanelTemplate;

public class DSUpdater extends RowUpdater {
	private DSValue dsValue;

	public DSUpdater(TablePanelTemplate template, 
			int idx, DSValue dsValue) {
		super(template, idx);
		this.dsValue = dsValue;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateRow() {
		Vector<Vector> rows = template.getTableModel().getDataVector();
		if (idx >= rows.size())
			throw new IllegalArgumentException(ModelConsts.indexOutBoundException);
		Vector row = rows.elementAt(idx);
		row.set(2, dsValue.getValue());
		template.getTableModel().fireTableDataChanged();
	}

}
