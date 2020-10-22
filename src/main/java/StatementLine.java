import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class StatementLine {
	
	private final Integer change;
	private final Integer balance;
	private final String date;
	
	public StatementLine(final Integer change, final Integer balance) {
		this.change = change;
		this.balance = balance;
		this.date = formatTodaysDate();
	}
	
	public StatementLine(final String date, final Integer change, final Integer balance) {
		this.change = change;
		this.balance = balance;
		this.date = date;
	}
	
	private String formatTodaysDate() {
		return new SimpleDateFormat("dd/MM/yyyy")
				.format(today());
	}
	
	private Date today() {
		return Date.from(Instant.now());
	}
	
	public String format() {
		return String.format("%s || %s   || %s", date, change, balance);
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final StatementLine that = (StatementLine) o;
		return Objects.equals(change, that.change) &&
				Objects.equals(balance, that.balance) &&
				Objects.equals(date, that.date);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(change, balance, date);
	}
}
