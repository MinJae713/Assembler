package dto.assembler;

import java.util.Vector;

import msConstants.ModelConsts;
import msConstants.ViewConsts;
import msModel.assembler.node.entity.DataAlloc;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;
import status.EParsingStatus;

public class ParsingInfo {
	private EParsingStatus status;
	private boolean newSymbol;
	private int tokenIdx;
	private String curToken;
	private int stackSize;
	private int heapSize;
	private DataAlloc dataAlloc;
	private Symbol symbol;
	private Instruction instruction;
	private int updateSymbolIdx;
	private boolean removeIdentifier;
	public ParsingInfo() {
		status = null;
		newSymbol = true;
		curToken = "";
		stackSize = 0;
		heapSize = 0;
		updateSymbolIdx = ModelConsts.noUpdate;
		removeIdentifier = false;
	}
	public EParsingStatus getStatus() {
		return status;
	}
	public boolean isNewSymbol() {
		return newSymbol;
	}
	public int getTokenIdx() {
		return tokenIdx;
	}
	public String getCurToken() {
		return curToken;
	}
	public int getStackSize() {
		return stackSize;
	}
	public int getHeapSize() {
		return heapSize;
	}
	public DataAlloc getDataAlloc() {
		return dataAlloc;
	}
	public Symbol getSymbol() {
		return symbol;
	}
	public Instruction getInstruction() {
		return instruction;
	}
	public int getUpdateSymbolIdx() {
		return updateSymbolIdx;
	}
	public boolean isRemoveIdentifier() {
		return removeIdentifier;
	}
	public String getNextToken(Vector<String> tokenVector) {
		if (tokenIdx+1 != tokenVector.size())
			return tokenVector.get(tokenIdx+1);
		else return ViewConsts.Parser.NextTokenDS.no;
	}
	public void setStatus(EParsingStatus status) {
		this.status = status;
	}
	public void setNewSymbol(boolean newSymbol) {
		this.newSymbol = newSymbol;
	}
	public void setTokenIdx(int tokenIdx) {
		this.tokenIdx = tokenIdx;
	}
	public void setCurToken(String curToken) {
		this.curToken = curToken;
	}
	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}
	public void setHeapSize(int heapSize) {
		this.heapSize = heapSize;
	}
	public void setDataAlloc(DataAlloc dataAlloc) {
		this.dataAlloc = dataAlloc;
	}
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}
	public void setUpdateSymbolIdx(int updateSymbolIdx) {
		this.updateSymbolIdx = updateSymbolIdx;
	}
	public void setRemoveIdentifier(boolean removeIdentifier) {
		this.removeIdentifier = removeIdentifier;
	}
}
