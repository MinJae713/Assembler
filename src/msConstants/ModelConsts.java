package msConstants;

public class ModelConsts {
	public static final String indexOutBoundException = "[유효하지 않은 인덱스입니다]";
	public static final int clearSelection = Integer.MIN_VALUE;
	public static final String csName = "Code";
	public static final String dsName = "Data";
	public static final String ssName = "Stack";
	public static final String hsName = "Heap";
	public static final String symbolNotFoundException = "지정 변수 해당 심볼을 못찾았습니다";
	public static final String noProgramException = "프로그램이 만들어지지 않았습니다";
	public static final String noHSException = "헤더 세그먼트가 만들어지지 않았습니다";
	public static final String noDSException = "데이터 세그먼트가 만들어지지 않았습니다";
	public static final String noCSException = "코드 세그먼트가 만들어지지 않았습니다";
	public static final int noUpdate = Integer.MIN_VALUE;
	public static final String addException = "add op2는 셋 중 하나여야 합니다";
	public static final String cmpRegiException = "cmp op1=레지스터 - op2는 셋 중 하나여야 합니다";
	public static final String cmpVarException = "cmp op1=변수 - op2는 셋 중 하나여야 합니다";
	public static final String cmpException = "cmp op1은 둘 중 하나여야 합니다";
	public static final String moveRegiException = "move op1=레지스터 - op2는 셋 중 하나여야 합니다";
	public static final String moveVarException = "move op1=변수 - op2는 둘 중 하나여야 합니다";
	public static final String moveException = "move op2는 둘 중 하나여야 합니다";
	public static final String instructionException = "명령어는 셋 중 하나여야 합니다";
	public static final String operandException = "오퍼랜드는 셋 중 하나여야 합니다";
	public static final String segNameException = "-해당 이름의 세그먼트가 없습니다";
	
	public static final String noAddressException = "지정 주소에 해당하는 데이터가 없습니다";
	public static final int dsValueInit = 0;
}
