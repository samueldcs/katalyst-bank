public class AccountService {
    
    private final StatementPrinter statementPrinter;
    private Integer balance = 0;
    
    AccountService(final StatementPrinter statementPrinter) {
        this.statementPrinter = statementPrinter;
    }
    
    public void deposit(int amount)  {
        updateBalance(amount);
        registerTransaction(amount);
    }
    
    
    public void withdraw(int amount) {
        updateBalance(-amount);
        registerTransaction(-amount);
    }
    
    public void printStatement() {
        statementPrinter.print();
    }
    
    private void registerTransaction(final int amount) {
        statementPrinter.addStatement(new StatementLine(amount, balance));
    }
    
    private void updateBalance(final int amount) {
        this.balance += amount;
    }
    
}









