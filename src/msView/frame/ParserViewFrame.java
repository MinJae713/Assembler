package msView.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import dto.assembler.ParsingInfo;
import msConstants.ViewConsts;
import msRun.Init.ConvertActionHandler;
import msView.parserPanel.ParserStatusPanel;

public class ParserViewFrame extends StatusViewFrame {
	private static final long serialVersionUID = 1L;
	private ParserStatusPanel statusPanel;
	
	public ParserViewFrame(ConvertActionHandler convertFrameListener) {
		super(convertFrameListener);
	}
	@Override
	protected Dimension getFrameDim() {
		return ViewConsts.Parser.viewDim;
	}
	@Override
	protected String getFrameTitle() {
		return ViewConsts.Parser.viewTitle;
	}
	@Override
	protected JButton createConvertButton() {
		JButton button = new JButton(ViewConsts.Parser.buttonName);
		button.setActionCommand(ViewConsts.Parser.actionCommand);
		return button;
	}
	@Override
	protected String getNextButtonTitle() {
		return ViewConsts.Parser.nextButtonTitle;
	}
	@Override
	protected void addStatusPanel() {
		statusPanel = new ParserStatusPanel(assembleController);
		add(statusPanel, BorderLayout.CENTER);
	}
	@Override
	protected void updateView() {
		ParsingInfo parsingInfo = assembleController.parsing();
		statusPanel.updateView(parsingInfo);
	}
	@Override
	public void initialize() {
		super.initialize();
		Vector<String> tokenVector = assembleController.initParsing();
		setMaxCount(tokenVector.size());
		statusPanel.initialize(tokenVector);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				nextButton.requestFocus();
			}
		});
	}
}
