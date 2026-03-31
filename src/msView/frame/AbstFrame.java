package msView.frame;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JFrame;

import msController.assembler.AssembleController;
import msController.emulator.EmulController;
import msRun.Init.ConvertActionHandler;

public abstract class AbstFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	protected AssembleController assembleController;
	protected EmulController emulController;
	protected JButton convertButton;
	
	public AbstFrame(ConvertActionHandler listener) {
		GraphicsEnvironment environment = 
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		Dimension dimension = getFrameDim();
		int x = environment.getCenterPoint().x-(int)dimension.getWidth()/2;
		int y = environment.getCenterPoint().y-(int)dimension.getHeight()/2;
		convertButton = createConvertButton();
		convertButton.addActionListener(listener);
		convertButton.addKeyListener(listener);
		setSize(dimension);
		setLocation(x, y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(getFrameTitle());
	}
	protected abstract Dimension getFrameDim();
	protected abstract String getFrameTitle();
	protected abstract JButton createConvertButton();
	public String getSource() {return null;}
	public void setSource() {};
	public abstract void initialize();
	public void associateAssemble(AssembleController controller) {
		this.assembleController = controller;
	}
	public void associateEmul(EmulController emulController) {
		this.emulController = emulController;
	}
	public JButton getConvertButton() {
		return convertButton;
	}
}
