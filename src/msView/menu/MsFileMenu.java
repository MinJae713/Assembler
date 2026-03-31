package msView.menu;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MsFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	public enum EFileMenu {
		eNew("new", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)), 
		eSave("저장",KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)), 
		eLoad("불러오기", KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		private String name;
		private KeyStroke keyStroke;
		private EFileMenu(String name, KeyStroke keyStroke) {
			this.name = name;
			this.keyStroke = keyStroke;
		}
		public String getName() {
			return name;
		}
		public KeyStroke getKeyStroke() {
			return keyStroke;
		}
	}
	public MsFileMenu(ActionListener actionListener) {
		super("파일");
		for(EFileMenu menus : EFileMenu.values()) {
			JMenuItem item = new JMenuItem(menus.getName());
			item.setActionCommand(menus.toString());
			item.addActionListener(actionListener);
			item.setAccelerator(menus.getKeyStroke());
			this.add(item);
		}
	}
}
