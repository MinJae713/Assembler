package msController.assembler;

import java.util.List;
import java.util.Vector;

import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import dto.common.mI.MInstruction;
import dto.emulator.ProcessControlBlock;
import msModel.assembler.node.entity.DataAlloc;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;

public interface AssembleController {
	public List<DataAlloc> getHeaderAllocs();
	public List<DataAlloc> getDataSegment();
	public Vector<MInstruction> getMIs();
	public ProcessControlBlock getPCB();
	public void setAssemblyCode(String assemblyCode);
	public Vector<String> initParsing();
	public ParsingInfo parsing();
	public GenerateInfo generating();
	public void fixHeaderAllocs();
	public Vector<Symbol> getSymbols();
	public Vector<Instruction> getInstructions();
	public Vector<String> getTokenVector(String assemblyCode);
}
