package oms;

import java.util.Optional;
import java.util.Random;

public class Order {
	// dummy for
	static Random r = new java.util.Random(System.currentTimeMillis());
	private long orderId;
	private int version;
	private int price;
	private String userId;
	private OrderType type;

	private Order() {
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public OrderType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
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
		Order other = (Order) obj;
		return orderId == other.orderId;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public static Order create(Optional<Order> modelOrder) {
		Order o = new Order();
		if (modelOrder.isPresent()) {
			o.setUserId(modelOrder.get().getUserId());
			o.setOrderId(modelOrder.get().getOrderId());
			o.setPrice(modelOrder.get().getPrice());
			o.setVersion(modelOrder.get().getVersion() + 1);
			o.setType(modelOrder.get().getType());
		} else {
			o.setUserId("userId");
			o.setOrderId(System.currentTimeMillis());
			o.setPrice(r.nextInt(5));
			o.setType(OrderType.SELL);
			o.setVersion(1);
		}
		return o;
	}

}
