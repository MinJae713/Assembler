package msView.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import dto.assembler.GenerateInfo;
import msConstants.ViewConsts;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;
import msRun.Init.ConvertActionHandler;
import msView.generatorPanel.GenerateStatusPanel;

public class GeneratorViewFrame extends StatusViewFrame {
	private static final long serialVersionUID = 1L;
	private GenerateStatusPanel statusPanel;

	public GeneratorViewFrame(ConvertActionHandler convertFrameListener) {
		super(convertFrameListener);
	}
	@Override
	protected Dimension getFrameDim() {
		return ViewConsts.Generator.viewDim;
	}
	@Override
	protected String getFrameTitle() {
		return ViewConsts.Generator.viewTitle;
	}
	@Override
	protected JButton createConvertButton() {
		JButton button = new JButton(ViewConsts.Generator.buttonName);
		button.setActionCommand(ViewConsts.Generator.actionCommand);
		return button;
	}
	@Override
	protected String getNextButtonTitle() {
		return ViewConsts.Generator.nextButtonTitle;
	}
	@Override
	protected void addStatusPanel() {
		statusPanel = new GenerateStatusPanel();
		add(statusPanel, BorderLayout.CENTER);
	}
	@Override
	protected void updateView() {
		GenerateInfo generateInfo = assembleController.generating();
		statusPanel.updateView(generateInfo);
	}
	@Override
	protected void fixSegmentAlloc() {
		assembleController.fixHeaderAllocs();
	}
	@Override
	public void initialize() {
		super.initialize();
		Vector<Symbol> symbols = assembleController.getSymbols();
		Vector<Instruction> instructions = 
				assembleController.getInstructions();
		setMaxCount(instructions.size());
		statusPanel.initialize(symbols, instructions);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				nextButton.requestFocus();
			}
		});
	}
}
