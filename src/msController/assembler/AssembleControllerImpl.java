package msController.assembler;

import java.util.List;
import java.util.Vector;

import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import dto.common.mI.MInstruction;
import dto.emulator.ProcessControlBlock;
import msModel.Assembler;
import msModel.assembler.node.entity.DataAlloc;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.symbolTable.Symbol;

public class AssembleControllerImpl implements AssembleController {
	private Assembler assembler;
	public AssembleControllerImpl() {
		assembler = new Assembler();
	}
	@Override
	public List<DataAlloc> getHeaderAllocs() {
		return assembler.getHeaderAllocs();
	}
	@Override
	public List<DataAlloc> getDataSegment() {
		return assembler.getDataSegment();
	}
	@Override
	public Vector<MInstruction> getMIs() {
		return assembler.getMInstructions();
	}
	@Override
	public ProcessControlBlock getPCB() {
		return assembler.getPCB();
	}
	@Override
	public void setAssemblyCode(String assemblyCode) {
		assembler.setAssemblyCode(assemblyCode);
	}
	@Override
	public Vector<String> initParsing() {
		return assembler.initialize();
	}
	@Override
	public ParsingInfo parsing() {
		return assembler.parse();
	}
	@Override
	public GenerateInfo generating() {
		return assembler.generate();
	}
	@Override
	public void fixHeaderAllocs() {
		assembler.fixHeaderAllocs();
	}
	@Override
	public Vector<Symbol> getSymbols() {
		return assembler.getSymbolTable();
	}
	@Override
	public Vector<Instruction> getInstructions() {
		return assembler.getInstructions();
	}
	@Override
	public Vector<String> getTokenVector(String assemblyCode) {
		return assembler.getTokenVector(assemblyCode);
	}
}
