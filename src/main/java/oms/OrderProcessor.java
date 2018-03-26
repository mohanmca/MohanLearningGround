package oms;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderProcessor {
	private Set<Order> bestSell = new TreeSet<Order>(new BestSellOrder());
	private Map<Integer, Order> orders = new ConcurrentHashMap<Integer, Order>();

	public void processOrder(Order order) {
		Order oldOrder = orders.putIfAbsent(order.getOrderId(), order);
		if (oldOrder.equals(order)) {
			// just inserted - new order..
		} else {
			// old one updated - old order..
			trigger(order);
		}
	}

	private void trigger(Order order) {
		bestSell.add(order);
		System.out.println(bestSell.iterator().next());
	}

	static class BestSellOrder implements java.util.Comparator<Order> {
		@Override
		public int compare(Order o1, Order o2) {
			return o1.getPrice() - o1.getPrice();
		}
	}
	
	public static void main(String args[]) {
		
	}

}
