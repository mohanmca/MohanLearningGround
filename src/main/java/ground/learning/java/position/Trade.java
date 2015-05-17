package ground.learning.java.position;

public class Trade {
	public String trader, broker, symbol;
	public int quantity;
	public float price;
	private boolean shortTrade = false;

	public Trade(String _trader, String _broker, String _symbol, int _quantity,
			float _price) {
		this.trader = _trader;
		this.broker = _broker;
		this.symbol = _symbol;
		this.quantity = _quantity;
		this.price = _price;
		if (quantity < 0)
			shortTrade = true;
	}

	public boolean isShort() {
		return shortTrade;
	}

	public boolean isLong() {
		return !isShort();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((broker == null) ? 0 : broker.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + quantity;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		Trade other = (Trade) obj;
		if (broker == null) {
			if (other.broker != null)
				return false;
		} else if (!broker.equals(other.broker))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (trader == null) {
			if (other.trader != null)
				return false;
		} else if (!trader.equals(other.trader))
			return false;
		return true;
	}

}
