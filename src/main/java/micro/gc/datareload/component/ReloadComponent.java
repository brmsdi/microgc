package micro.gc.datareload.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@EnableScheduling
public class ReloadComponent {
    private static final String TIME_ZONE = "America/Manaus";

    @Autowired
    private WebClient webClient;

    @Scheduled(cron = "0 52 22 * * *", zone = TIME_ZONE)
    public void executeReload()
    {
        Mono<String> responseEntityMono = webClient
                .put()
                .uri(reload("INSERT_YES"))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                     if (response.statusCode().equals(HttpStatus.OK)) {
                         return response.bodyToMono(String.class);
                     }
                     else {
                         return response.createException().flatMap(Mono::error);
                     }
                 });
        responseEntityMono.subscribe(System.out::println);
    }

    public WebClient webClient(WebClient.Builder builder)
    {
        return builder
                .baseUrl("http://192.168.15.2:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String reload(String insert)
    {
        return String.format("/data/reload?insert=%s", insert);
    }
}