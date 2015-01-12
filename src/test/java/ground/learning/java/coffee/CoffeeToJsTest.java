package ground.learning.java.coffee;


import ground.learning.java.coffee.CoffeeToJs;

import javax.script.ScriptException;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class CoffeeToJsTest {
	static CoffeeToJs coffeeToJs = new CoffeeToJs();

	@Test
	public void empty() throws ScriptException {
		String js = new CoffeeToJs().toJs("");
		Assert.assertThat(js, CoreMatchers.equalTo("\n"));
	}

	@Test
	public void to_javascript() throws ScriptException {
		String js = coffeeToJs.toJs("life=42");

		Assert.assertThat(js, CoreMatchers.equalTo("var life;\n\nlife = 42;\n"));
	}
	
	@Test
	public void exec_javascript() throws ScriptException {
		String js = coffeeToJs.toJs(coffeeToJs.readScript("/js/testCoffee.js"));
		coffeeToJs.executeJs(js);

		//Check console ouput for "Hello, CoffeeScript World!"
	}
	
	
	
}