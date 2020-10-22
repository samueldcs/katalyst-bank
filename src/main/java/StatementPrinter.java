import java.util.Stack;

public class StatementPrinter {
	
	public static final String STATEMENT_HEADER = "Date       || Amount || Balance";
	private final Stack<StatementLine> statementLines = new Stack<>();
	
	public void addStatement(StatementLine statementLine) {
		this.statementLines.add(statementLine);
	}
	
	public void print() {
		this.printHeader();
		this.printStatementLines();
	}
	
	private void printHeader() {
		this.printString(STATEMENT_HEADER);
	}
	
	private void printStatementLines() {
		while(statementLines.size() > 0)
			this.printString(statementLines.pop().format());
	}
	
	private void printString(final String content) {
		System.out.println(content);
	}
}
