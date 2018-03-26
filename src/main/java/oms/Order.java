package oms;

import java.util.Random;

public class Order {
	//dummy for 
	static Random r = new java.util.Random(System.currentTimeMillis());
	private long orderId;
	private int version;
	private int price;
	
	private Order() {	
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	private String userId;
	private OrderType type;

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

	public void setType(OrderType type) {
		this.type = type;
	}
	
	public static Order create(order Optional) {
		Order o  = new Order();
		o.setUserId("userId");
		o.setOrderId(System.currentTimeMillis());
		o.setPrice(r.nextInt(5));
		o.setVersion(version);
		return o;
	}

}
