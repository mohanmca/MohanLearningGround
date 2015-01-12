package ground.learning.java.coffee;

import static javax.script.ScriptContext.ENGINE_SCOPE;

import java.io.IOException;
import java.io.InputStream;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;

public class CoffeeToJs {
	private final CompiledScript compiledScript;
	private final Bindings bindings;

	public CoffeeToJs() {
		String script = readScript("/js/coffee-script.js");

		ScriptEngine nashorn = new ScriptEngineManager()
				.getEngineByName("nashorn");
		try {
			compiledScript = ((Compilable) nashorn).compile(script
					+ "\nCoffeeScript.compile(__source, {bare: true});");
			bindings = nashorn.getBindings(ENGINE_SCOPE);

		} catch (ScriptException e) {
			throw new RuntimeException("Unable to compile script", e);
		}
	}

	public static String readScript(String path) {
		try (InputStream input = CoffeeToJs.class.getResourceAsStream(path)) {
			return IOUtils.toString(input);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read " + path, e);
		}
	}

	public synchronized String toJs(String coffee) throws ScriptException {
		bindings.put("__source", coffee);

		return compiledScript.eval(bindings).toString();
	}

	public synchronized Object executeJs(String script) throws ScriptException {
		ScriptEngine nashorn = new ScriptEngineManager()
				.getEngineByName("nashorn");
		nashorn.eval("console = {   log: print,    warn: print,    error: print};");
		return nashorn.eval(script);
	}

}