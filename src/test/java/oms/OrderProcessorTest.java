package oms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Optional;

public class OrderProcessorTest {

	@Before
	public void clear() {
		OrderProcessor.getInstance().clear();
	}

	@Test
	public void singleOrder() {
		Order order = Order.create(Optional.empty());
		OrderProcessor.getInstance().processOrder(order);
		Assert.assertTrue(OrderProcessor.getInstance().bestOrder(OrderType.SELL).equals(order));
	}

	@Test
	public void bestOfTwoSellOrders() {
		Order modelOrder = Order.create(Optional.empty());
		Order orderSecond = Order.create(Optional.empty());
		modelOrder.setPrice(10);
		orderSecond.setPrice(modelOrder.getPrice() - 1);

		OrderProcessor.getInstance().processOrder(modelOrder);
		OrderProcessor.getInstance().processOrder(orderSecond);

		Assert.assertEquals(OrderProcessor.getInstance().traderBlotterSize(), 2);
		Assert.assertEquals(OrderProcessor.getInstance().bestOrder(OrderType.SELL), orderSecond);
		Assert.assertEquals(OrderProcessor.getInstance().bestOrder(OrderType.SELL).getPrice(), orderSecond.getPrice());
	}

	@Test
	public void bestOfTwoBuyOrders() {
		Order modelOrder = Order.create(Optional.empty());
		modelOrder.setType(OrderType.BUY);
		modelOrder.setPrice(10);

		Order orderSecond = Order.create(Optional.of(modelOrder));
		orderSecond.setPrice(modelOrder.getPrice() + 10);

		OrderProcessor.getInstance().processOrder(modelOrder);
		OrderProcessor.getInstance().processOrder(orderSecond);

		Assert.assertEquals(OrderProcessor.getInstance().traderBlotterSize(), 2);
		Assert.assertEquals(OrderProcessor.getInstance().bestOrder(OrderType.BUY), orderSecond);
		Assert.assertEquals(OrderProcessor.getInstance().bestOrder(OrderType.BUY).getPrice(), orderSecond.getPrice());
	}
}
