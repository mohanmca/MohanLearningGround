package ground.learning.java.position;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class BoxedPositionCalculatorTest {

	@Test
	public void testEmpty() {
		NetPositionCalculator calculator = new NetPositionCalculator();
		Collection<Position> result = calculator.getOutstandingPosition();
		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void testSingleTrade() {
		NetPositionCalculator calculator = new NetPositionCalculator();
		Trade trade = new Trade("Joe", "ML", "IBM.N", 100, 50);
		calculator.add(trade);
		Collection<Position> result = calculator.getOutstandingPosition();
		Assert.assertEquals(result.size(), 1);
		Position pos = new Position("Joe");
		pos.update("IBM.N", 100);
		Assert.assertTrue(result.toArray()[0].equals(pos));
	}

	@Test
	public void testThreeTradeForSameSymbol() {
		NetPositionCalculator calculator = new NetPositionCalculator();
		Trade trade = new Trade("Joe", "ML", "IBM.N", 100, 50);
		calculator.add(trade);
		trade = new Trade("Joe", "DB", "IBM.N", -50, 50);
		calculator.add(trade);
		trade = new Trade("Joe", "CS", "IBM.N", 30, 30);
		calculator.add(trade);

		Collection<Position> result = calculator.getOutstandingPosition();
		Assert.assertEquals(result.size(), 1);
		Position pos = new Position("Joe");
		pos.update("IBM.N", 80);
		Assert.assertTrue(result.toArray()[0].equals(pos));
	}

	@Test
	public void testForMultipleTrades() {
		NetPositionCalculator calculator = new NetPositionCalculator();
		Trade trade = new Trade("Joe", "ML", "IBM.N", 100, 50);
		calculator.add(trade);
		trade = new Trade("Joe", "DB", "IBM.N", -50, 50);
		calculator.add(trade);
		trade = new Trade("Joe", "CS", "IBM.N", 30, 30);
		calculator.add(trade);
		trade = new Trade("Mike", "CS", "AAPL.N", 100, 20);
		calculator.add(trade);
		trade = new Trade("Mike", "BC", "AAPL.N", 200, 20);
		calculator.add(trade);
		trade = new Trade("Debby", "BC", "NVDA.N", 500, 20);
		calculator.add(trade);

		Position position = calculator.getPositionFor("Mike");
		Position pos = new Position("Mike");
		pos.update("AAPL.N", 300);
		Assert.assertEquals(pos, position);
	}
}