import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class StatementLineTest {
	
	@Test
	@DisplayName("A statement line can correctly format itself")
	public void formats() {
		var line = new StatementLine("20/10/2020", 200, 400);
		assertEquals("20/10/2020 || 200   || 400", line.format());
	}
	
	@Test
	@DisplayName("When built without a date, the statement will use today's")
	public void findsDate() {
		var line = new StatementLine(200, 400);
		assertEquals(String.format("%s || 200   || 400", today()), line.format());
	}
	
	private String today() {
		return new SimpleDateFormat("dd/MM/yyyy")
				.format(Date.from(Instant.now()));
	}
}
