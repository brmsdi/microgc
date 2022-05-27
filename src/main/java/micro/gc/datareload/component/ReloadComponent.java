package micro.gc.datareload.component;

import lombok.extern.slf4j.Slf4j;
import micro.gc.datareload.pojos.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ReloadComponent {

    @Autowired
    @Qualifier("webClientReload")
    private WebClient webClientReload;

    public Mono<String> executeReload(TokenDTO tokenDTO, String Insert)
    {
        log.info("Init reload");
        return webClientReload
                .put()
                .uri(reload(Insert))
                .header("Authorization", String.format("%s %s", tokenDTO.getType(), tokenDTO.getToken()))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    }
                    else {
                        log.error("Erro ao resetar dados");
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }

    private String reload(String insert)
    {
        return String.format("/data/reload?insert=%s", insert);
    }

}