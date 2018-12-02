package ground.learning.java.coffee;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

class CoffeScriptOnJdk {
	public static void main(String args[]) throws ScriptException {
		InputStream testScript = CoffeScriptOnJdk.class.getClass()
				.getResourceAsStream("/js/testCoffee.js");
		getLoadedEngine().eval(new InputStreamReader(testScript));

	}

	public static ScriptEngine getEngine() {
		new ScriptEngineManager().getEngineFactories().forEach(
				action -> System.out.println(action.getEngineName()));
		ScriptEngineManager mgr = new ScriptEngineManager();
		return mgr.getEngineByName("Nashorn");
	}

	public static ScriptEngine getLoadedEngine() {
		ScriptEngine engine = getEngine();
		try {
			InputStream coffeeStream = CoffeScriptOnJdk.class.getClass()
					.getResourceAsStream("/js/coffee-script.js");
			InputStream jqueryStream = CoffeScriptOnJdk.class.getClass()
					.getResourceAsStream("/js/jquery-2.1.3.js");

//			SequenceInputStream is = new SequenceInputStream(coffeeStream,
//					jqueryStream);
			engine.eval(new InputStreamReader(coffeeStream));
		} catch (ScriptException e) {
			throw new RuntimeException(e);
		}
		return engine;
	}

}