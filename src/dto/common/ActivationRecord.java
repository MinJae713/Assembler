package dto.common;

import regiEnums.ERecordType;

public class ActivationRecord {
	private String funtionName;
	private ERecordType recordType;
	private int size;
	public ActivationRecord() {
		
	}
	public ActivationRecord(String funtionName, ERecordType recordType, int size) {
		this.funtionName = funtionName;
		this.recordType = recordType;
		this.size = size;
	}
	public String getFuntionName() {
		return funtionName;
	}
	public ERecordType getRecordType() {
		return recordType;
	}
	public int getSize() {
		return size;
	}
	public void setFuntionName(String funtionName) {
		this.funtionName = funtionName;
	}
	public void setRecordType(ERecordType recordType) {
		this.recordType = recordType;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
