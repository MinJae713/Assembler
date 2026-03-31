package msView.codePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dto.common.mI.MInstruction;
import msConstants.ViewConsts;
import msModel.assembler.node.entity.DataAlloc;
import msView.codePanel.textPanel.SourcePanel;

public class CodePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private SourcePanel assemblyPanel;
	private AssemblyToMIPanel aTMIPanel;
	private SourcePanel miPanel;
	public CodePanel(ActionListener assembleEventHandler) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		assemblyPanel = new SourcePanel(
				ViewConsts.Code.assemblyLabel, 
				ViewConsts.Code.assemblyEditable,
				ViewConsts.Code.assemblyDim);
		aTMIPanel = new AssemblyToMIPanel(assembleEventHandler);
		miPanel = new SourcePanel(
				ViewConsts.Code.mILabel, 
				ViewConsts.Code.mIEditable,
				ViewConsts.Code.mIDim);
		add(assemblyPanel, BorderLayout.WEST);
		add(aTMIPanel, BorderLayout.CENTER);
		add(miPanel, BorderLayout.EAST);
	}
	public void initialize() {
		setAssemblyCode("");
		aTMIPanel.initialize();
	}
	public String getAssemblyCode() {
		return assemblyPanel.getCode();
	}
	public void setAssemblyCode(String sourceCode) {
		assemblyPanel.setCode(sourceCode);
		miPanel.setCode("");
	}
	public String getMICode() {
		return miPanel.getCode();
	}
	public void setMICode(String sourceCode) {
		miPanel.setCode(sourceCode);
	}
	public void setResult(List<DataAlloc> headerAllocs,
			List<DataAlloc> dataAllocs, List<MInstruction> mInstructions) {
		aTMIPanel.setTableResult(headerAllocs, dataAllocs);
		StringBuffer buffer = new StringBuffer();
		for (MInstruction mInstruction : mInstructions)
			buffer.append(mInstruction.getConvertToHex()+"\n");
		miPanel.setCode(buffer.toString());
	}
}
