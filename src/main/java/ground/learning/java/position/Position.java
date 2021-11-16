package ground.learning.java.position;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;



public class Position {

	public String trader;
	private final Map<String, SymbolPosition> outStanding = new HashMap<String, SymbolPosition>();

	public Position(String _trader) {
		this.trader = _trader;
	}

	public void update(Trade trade) {
		update(trade.symbol, trade.quantity);
	}

	public void update(String symbol, int qty) {
		SymbolPosition position = outStanding.get(symbol);
		if (position == null) {
			position = new SymbolPosition(symbol, 0);
			outStanding.put(symbol, position);
		}
		position.update(qty);
	}

	@Override
	public String toString() {
		return "Position [trader=" + trader + ", outStanding=" + Arrays.deepToString(outStanding.values().toArray())
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((outStanding == null) ? 0 : outStanding.hashCode());
		result = prime * result + ((trader == null) ? 0 : trader.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (trader == null) {
			if (other.trader != null)
				return false;
		} else if (!trader.equals(other.trader))
			return false;
		if (outStanding == null) {
			return other.outStanding == null;
		} else return outStanding.equals(other.outStanding);
	}

	private static class SymbolPosition {
		private final String symbol;
		private int quantity;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + quantity;
			result = prime * result
					+ ((symbol == null) ? 0 : symbol.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SymbolPosition other = (SymbolPosition) obj;
			if (quantity != other.quantity)
				return false;
			if (symbol == null) {
				return other.symbol == null;
			} else return symbol.equals(other.symbol);
		}

		public SymbolPosition(String _symbol, int _quantity) {
			this.symbol = _symbol;
			this.quantity = _quantity;
		}

		public void update(int qty) {
			quantity = quantity + qty;
		}

		@Override
		public String toString() {
			return "SymbolPosition [symbol=" + symbol + ", quantity=" + quantity + "]";
		}
	}

}
