import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AccountServiceTest {
	
	private StatementPrinter printer = mock(StatementPrinter.class);
	
	@Test
	@DisplayName("The service will forward one balance change (deposit) to the printer.")
	public void registersOneDeposit() {
		var service = anAccountService();
		service.deposit(200);
		verify(printer).addStatement(new StatementLine(200, 200));
	}
	
	@Test
	@DisplayName("The service will forward one balance change (withdrawal) to the printer.")
	public void registersOneWithdrawal() {
		var service = anAccountService();
		service.withdraw(200);
		verify(printer).addStatement(new StatementLine(-200, -200));
	}
	
	@Test
	@DisplayName("The service will forward two balance changes (deposits) to the printer, keeping track of the balance.")
	public void registersTwoDeposits() {
		var service = anAccountService();
		service.deposit(200);
		verify(printer).addStatement(new StatementLine(200, 200));
		service.deposit(200);
		verify(printer).addStatement(new StatementLine(200, 400));
	}
	
	@Test
	@DisplayName("The service will forward two balance changes (withdrawals) to the printer, keeping track of the balance.")
	public void registersTwoWithdrawals() {
		var service = anAccountService();
		service.withdraw(200);
		verify(printer).addStatement(new StatementLine(-200, -200));
		service.withdraw(200);
		verify(printer).addStatement(new StatementLine(-200, -400));
	}
	
	@Test
	@DisplayName("The service will keep track on the balance with deposits and withdrawals.")
	public void keepsTrackOfBalance() {
		var service = anAccountService();
		service.deposit(1000);
		verify(printer).addStatement(new StatementLine(1000, 1000));
		service.deposit(2000);
		verify(printer).addStatement(new StatementLine(2000, 3000));
		service.withdraw(500);
		verify(printer).addStatement(new StatementLine(-500, 2500));
	}
	
	@Test
	@DisplayName("The service will print the statement using the provided printer")
	public void printsStatement() {
		var service = anAccountService();
		service.withdraw(200);
		service.printStatement();
		verify(printer).print();
	}
	
	private AccountService anAccountService() {
		return new AccountService(printer);
	}
	
}
