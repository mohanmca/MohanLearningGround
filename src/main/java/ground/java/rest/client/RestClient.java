package ground.java.rest.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class RestClient {
	public static void main(String[] args) throws InterruptedException {
		var userName = "mohanmca";
		var user = "https://api.github.com/users/" + userName;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(user)).build();
		HttpClient client = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS)
				.connectTimeout(Duration.ofSeconds(10)).build();

		BodyHandler<Path> bodyHandler = (rspInfo) -> rspInfo.statusCode() == 200
				? BodySubscribers.ofFile(Paths.get("c:/tmp/" + userName + ".json"))
				: BodySubscribers.replacing(Paths.get("c:/tmp/" + userName + ".json"));

		 //client.sendAsync(request, bodyHandler);
		 //client.sendAsync(request, System.out::println);

		client.sendAsync(request, bodyHandler).thenApply(HttpResponse::body).thenAccept(System.out::println);
				
	}
}
