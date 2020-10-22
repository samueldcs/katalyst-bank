import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatementPrinterTest {
	
	@Test
	@DisplayName("Given an empty statement stack, the printer will return nothing but the header")
	public void printsHeader() {
		var printer = aStatementPrinter();
		var output = this.collectOutputFrom(printer::print);
		
		assertTrue(startsWithHeader(output));
		assertEquals(output.size(), 1);
	}
	
	
	@Test
	@DisplayName("Given a transaction, the printer will print it after the header")
	public void printsTransaction() {
		var printer = aStatementPrinter();
		var output = this.collectOutputFrom(() -> {
			printer.addStatement(new StatementLine(200, 200));
			printer.print();
		});
		
		assertTrue(startsWithHeader(output));
		assertEquals(format("%s || 200   || 200", today()), output.get(1));
		assertEquals(2, output.size());
	}
	
	@Test
	@DisplayName("Given two transactions, the printer will print them in the order of a stack, after the header")
	public void printsTransactions() {
		var printer = aStatementPrinter();
		var output = this.collectOutputFrom(() -> {
			printer.addStatement(new StatementLine("21/10/2020", 200, 200));
			printer.addStatement(new StatementLine("22/10/2020", 200, 400));
			printer.print();
		});
		
		assertTrue(startsWithHeader(output));
		assertEquals("22/10/2020 || 200   || 400", output.get(1));
		assertEquals("21/10/2020 || 200   || 200", output.get(2));
		assertEquals(3, output.size());
	}
	
	@Test
	@DisplayName("Given three transactions, the printer will print them in the order of a stack, after the header")
	public void printsMoreTransactionsIncludingNegative() {
		var printer = aStatementPrinter();
		var output = this.collectOutputFrom(() -> {
			printer.addStatement(new StatementLine("10/01/2012", 1000, 1000));
			printer.addStatement(new StatementLine("13/01/2012", 2000, 3000));
			printer.addStatement(new StatementLine("14/01/2012", -500, 2500));
			printer.print();
		});
		
		assertTrue(startsWithHeader(output));
		assertEquals("14/01/2012 || -500   || 2500", output.get(1));
		assertEquals("13/01/2012 || 2000   || 3000", output.get(2));
		assertEquals("10/01/2012 || 1000   || 1000", output.get(3));
		assertEquals(4, output.size());
	}
	
	private static String today() {
		return new SimpleDateFormat("dd/MM/yyyy")
				.format(Date.from(Instant.now()));
	}
	
	private boolean startsWithHeader(final List<String> output) {
		return output
				.get(0)
				.equals("Date       || Amount || Balance");
	}
	
	private StatementPrinter aStatementPrinter() {
		return new StatementPrinter();
	}
	
	private List<String> collectOutputFrom(final Runnable function) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		System.setOut(new PrintStream(bo));
		
		function.run();
		
		try {
			bo.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		var output = new String(bo.toByteArray());
		return Arrays.asList(output.split(System.getProperty("line.separator")));
	}
	
	
}
