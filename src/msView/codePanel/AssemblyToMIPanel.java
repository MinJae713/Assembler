package msView.codePanel;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import msConstants.ViewConsts;
import msModel.assembler.node.entity.DataAlloc;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.setTable.DataAllocTSetter;

public class AssemblyToMIPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private TablePanelTemplate headerPanel;
	private JButton button;
	private TablePanelTemplate dataPanel;
	public AssemblyToMIPanel(ActionListener assembleEventHandler) {
		button = new JButton(ViewConsts.Code.assemblyToMI);
		button.addActionListener(assembleEventHandler);
		button.setActionCommand(ViewConsts.Code.parserActionCommand);
		headerPanel = new TablePanelTemplate(
				ViewConsts.Table.headerLabel,
				ViewConsts.Table.dataAllocColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Table.dim);
		dataPanel = new TablePanelTemplate(
				ViewConsts.Table.dataLabel, 
				ViewConsts.Table.dataAllocColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Table.dim);
		add(button);
		add(headerPanel);
		add(dataPanel);
	}
	public void initialize() {
		headerPanel.initialize();
		dataPanel.initialize(); 
	}
	public void setTableResult(List<DataAlloc> headerAllocs, 
			List<DataAlloc> dataAllocs) {
		headerPanel.initialize();
		dataPanel.initialize();
		headerPanel.setTable(new DataAllocTSetter(headerPanel, headerAllocs));
		dataPanel.setTable(new DataAllocTSetter(dataPanel, dataAllocs));
	}
}
