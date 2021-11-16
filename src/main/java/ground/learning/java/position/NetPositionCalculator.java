package ground.learning.java.position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetPositionCalculator {

	private final Map<String, List<Trade>> trades = new HashMap<String, List<Trade>>();
	// For every trader, there should be position for every symbol
	private final Map<String, Position> positions = new HashMap<String, Position>();

	public Collection<Position> getOutstandingPosition() {
		if (trades.isEmpty())
			return java.util.Collections.emptyList();
		return positions.values();
	}

	public Position getPositionFor(String trader) {
		return positions.get(trader);
	}

	public void add(Trade trade) {
		addTrade(trade);
		updatePosition(trade);
	}

	private void addTrade(Trade trade) {
		List<Trade> traderTrades = trades.get(trade.trader);
		if (traderTrades == null) {
			traderTrades = new ArrayList<>();
			trades.put(trade.trader, traderTrades);
		}
		traderTrades.add(trade);
	}

	public void updatePosition(Trade trade) {
		Position traderPositions = positions.get(trade.trader);
		if (traderPositions == null) {
			traderPositions = new Position(trade.trader);
			positions.put(trade.trader, traderPositions);
		}
		traderPositions.update(trade);
	}
}
