package dto.common;

public class HeapObj {
	private String typeName;
	private int size;
	public HeapObj() {
		// TODO Auto-generated constructor stub
	}
	public HeapObj(String typeName, int size) {
		this.typeName = typeName;
		this.size = size;
	}
	public String getTypeName() {
		return typeName;
	}
	public int getSize() {
		return size;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
