package msRun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JOptionPane;

import msConstants.ViewConsts;
import msController.assembler.AssembleController;
import msController.assembler.AssembleControllerImpl;
import msController.emulator.EmulController;
import msController.emulator.EmulControllerImpl;
import msView.frame.AbstFrame;
import msView.frame.CodeViewFrame;
import msView.frame.EmulViewFrame;
import msView.frame.GeneratorViewFrame;
import msView.frame.ParserViewFrame;

public class Init {
	private CodeViewFrame codeViewFrame;
	private AbstFrame emulViewFrame;
	private AbstFrame parserViewFrame;
	private AbstFrame generatorViewFrame;
	private AssembleController assembleController;
	private EmulController emulController;
	public Init() {
		ConvertActionHandler actionListener = new ConvertActionHandler();
		assembleController = new AssembleControllerImpl();
		emulController = new EmulControllerImpl();
		codeViewFrame = new CodeViewFrame(actionListener);
		parserViewFrame = new ParserViewFrame(actionListener);
		generatorViewFrame = new GeneratorViewFrame(actionListener);
		emulViewFrame = new EmulViewFrame(actionListener);
		codeViewFrame.associateAssemble(assembleController);
		parserViewFrame.associateAssemble(assembleController);
		generatorViewFrame.associateAssemble(assembleController);
		codeViewFrame.associateEmul(emulController);
		emulViewFrame.associateEmul(emulController);
	}
	public void run() {
		codeViewFrame.initialize();
		codeViewFrame.setVisible(true);
	}
	public class ConvertActionHandler implements ActionListener, KeyListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals("code")) codeToEmul();
			else if (actionCommand.equals("com")) emulToCode();
			else if (actionCommand.equals("parser")) codeToParser();
			else if (actionCommand.equals("genCode")) parserToGen();
			else if (actionCommand.equals("genToCode")) genToCode();
		}
		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (e.getSource().equals(parserViewFrame.getConvertButton())) 
					parserToGen();
				else if (e.getSource().equals(generatorViewFrame.getConvertButton()))
					genToCode();
				else if (e.getSource().equals(emulViewFrame.getConvertButton())) 
					emulToCode();
			}
		}
	}
	private void codeToParser() {
		CodeViewFrame codeFrame = (CodeViewFrame)codeViewFrame;
		String assemblyCode = codeFrame.getAssemblyCode();
		if (assemblyCode.length() == 0 || !isStartToken(assemblyCode)) {
			JOptionPane.showMessageDialog(
					codeViewFrame, ViewConsts.Code.noAssemblyCodeMessage,
					ViewConsts.Code.noAssemblyCodeTitle, 
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (!isSegmentCorract(assemblyCode)) {
			JOptionPane.showMessageDialog(
					codeViewFrame, ViewConsts.Code.segmentMissmatch,
					ViewConsts.Code.segmentMissmatchTitle,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		codeViewFrame.setVisible(false);
		assembleController.setAssemblyCode(assemblyCode);
		parserViewFrame.initialize();
		parserViewFrame.setVisible(true);
	}
	private boolean isSegmentCorract(String assemblyCode) {
		List<String> tokens = assembleController.getTokenVector(assemblyCode);
		String[] segOrdinal = ViewConsts.Code.segmentOrdinal;
		int idx = 0;
		for (String token : tokens) {
			if (token.charAt(0) == '.') {
				token = token.substring(1);
				if (segOrdinal[idx].equals(token))
					idx++;
				else return false;
			}
		}
		return idx == 5; 
	}
	private boolean isStartToken(String assemblyCode) {
		String start = ".program";
		for (int i=0; i<start.length(); i++) 
			if (assemblyCode.charAt(i) != start.charAt(i))
				return false;
		return true;
	}
	private void parserToGen() {
		parserViewFrame.setVisible(false);
		generatorViewFrame.initialize();
		generatorViewFrame.setVisible(true);
	}
	private void genToCode() {
		generatorViewFrame.setVisible(false);
		CodeViewFrame codeFrame = (CodeViewFrame)codeViewFrame;
		codeFrame.setResult();
		codeViewFrame.setVisible(true);
		codeViewFrame.setAssembled(true);
	}
	private void codeToEmul() {
		if (!codeViewFrame.isAssembled()) {
			JOptionPane.showMessageDialog(
					codeViewFrame, ViewConsts.Code.noAssembledMessage,
					ViewConsts.Code.noAssembledTitle, 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		codeViewFrame.setVisible(false);
		emulController.initEmulator();
		emulController.setPCB(assembleController.getPCB());
		emulViewFrame.initialize();
		emulViewFrame.setVisible(true);
	}
	private void emulToCode() {
		emulViewFrame.setVisible(false);
		codeViewFrame.setVisible(true);
	}
}
