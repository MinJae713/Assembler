package msView.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.common.mI.MInstruction;
import msConstants.ViewConsts;
import msModel.assembler.node.entity.DataAlloc;
import msObserver.FileObserver;
import msRun.Init.ConvertActionHandler;
import msView.codePanel.CodePanel;
import msView.menu.MsMenuBar;

public class CodeViewFrame extends AbstFrame 
		implements FileObserver {
	private static final long serialVersionUID = 1L;
	private CodePanel codePanel;
	protected MsMenuBar menuBar;
	private boolean assembled;

	public CodeViewFrame(ConvertActionHandler listener) {
		super(listener);
		setLayout(new BorderLayout());
		menuBar = new MsMenuBar(this);
		setJMenuBar(menuBar);
		menuBar.setFileObserver(this);
		assembled = false;
		codePanel = new CodePanel(listener);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(convertButton);
		add(codePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
	}
	@Override
	protected Dimension getFrameDim() {
		return ViewConsts.Code.viewDim;
	}
	@Override
	protected JButton createConvertButton() {
		JButton button = new JButton(ViewConsts.Code.buttonName);
		button.setActionCommand(ViewConsts.Code.actionCommand);
		return button;
	}
	@Override
	protected String getFrameTitle() {
		return ViewConsts.Code.viewTitle;
	}
	@Override
	public void initialize() {
		assembled = false;
		assembleController.initParsing();
		codePanel.initialize();
	}
	@Override
	public String getAssemblyCode() {
		return codePanel.getAssemblyCode();
	}
	@Override
	public void setAssemblyCode(String source) {
		codePanel.setAssemblyCode(source);
	}
	@Override
	public String getSource() {
		return codePanel.getMICode();
	}
	public void setResult() {
		List<DataAlloc> headerAllocs = assembleController.getHeaderAllocs();
		List<DataAlloc> dataAllocs = assembleController.getDataSegment();
		List<MInstruction> mInstructions = assembleController.getMIs();
		codePanel.setResult(headerAllocs, dataAllocs, mInstructions);
	}
	public boolean isAssembled() {
		return assembled;
	}
	public void setAssembled(boolean assembled) {
		this.assembled = assembled;
	}
}
