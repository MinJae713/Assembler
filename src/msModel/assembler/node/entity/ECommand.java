package msModel.assembler.node.entity;

public enum ECommand {
	eAdd("add"),
	eMove("move"),
	eCmp("compare"),
	eJump("jump"),
	eGE("ge"),
	eHalt("halt"),
	eEnd(".end"),
	eLabel("");
	
	private String text;
	private ECommand(String text) {
		this.text = text;
	}
	public String getText() {
		return this.text;
	}
}
