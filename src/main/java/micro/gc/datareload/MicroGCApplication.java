package micro.gc.datareload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MicroGCApplication {

	@Value("${BASE_URL}")
	private String BASE_URL;

	@Bean(name = "webClientAuthentication")
	public WebClient webClientAuthentication(WebClient.Builder builder)
	{
		return builder
				.baseUrl(BASE_URL)
				.build();
	}

	@Bean(name = "webClientReload")
	public WebClient webClientReload(WebClient.Builder builder)
	{
		return builder
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
	public static void main(String[] args) {SpringApplication.run(MicroGCApplication.class, args);}
}
