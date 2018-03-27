package oms;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Logger;

/**
 * Best Buy Order - Highest bid price
 * ---- 
 * Best Sell Order - Lowest asking price
 * 
 * @author mohan
 */
public class OrderProcessor {

	private static Logger logger = Logger.getLogger("com.wombat.nose");

	private ConcurrentSkipListSet<Order> bestSell = new ConcurrentSkipListSet<Order>(new BestOrder().reversed());
	private ConcurrentSkipListSet<Order> bestBuy = new ConcurrentSkipListSet<Order>(new BestOrder());
	private Map<Long, Order> orders = new ConcurrentHashMap<Long, Order>();
	private static OrderProcessor _instance = new OrderProcessor();

	private OrderProcessor() {
	}

	public static OrderProcessor getInstance() {
		return _instance;
	}

	public void clear() {
		bestSell.clear();
		bestBuy.clear();
		orders.clear();
	}

	public void processOrder(Order order) {
		Order oldOrder = orders.put(order.getOrderId(), order);
		if (oldOrder == null || !oldOrder.equals(order)) {
			logger.finer("New order inserted! - oldOrder " + oldOrder + ", new order" + order.toString());
		} else {
			logger.finer("Existing order updated!" + order);
		}
		trigger(order);
	}

	private void trigger(Order order) {
		if (order.getType() == OrderType.BUY) {
			bestBuy.add(order);
		} else {
			bestSell.add(order);
		}
	}

	static class BestOrder implements java.util.Comparator<Order> {
		@Override
		public int compare(Order o1, Order o2) {
			return o1.getPrice() - o2.getPrice();
		}
	}

	public int traderBlotterSize() {
		return bestSell.size() + bestBuy.size();
	}

	public Order bestOrder(OrderType type) {
		if (type == OrderType.BUY) {
			return bestBuy.last();
		}
		return bestSell.last();
	}

	public static void main(String args[]) {

	}

}
