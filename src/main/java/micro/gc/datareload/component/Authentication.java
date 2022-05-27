package micro.gc.datareload.component;

import lombok.extern.slf4j.Slf4j;
import micro.gc.datareload.pojos.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Authentication {

    @Autowired
    @Qualifier("webClientAuthentication")
    private WebClient webClientAuthentication;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    public Mono<TokenDTO> prepareAuth()
    {
        log.info("Init authentication");
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        return webClientAuthentication
                .post()
                .uri(uriAuthEmployee(params))
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().equals(HttpStatus.OK))
                    {
                        return clientResponse.bodyToMono(TokenDTO.class);
                    } else
                    {
                        log.error("Erro ao logar");
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                });
    }

    public String uriAuthEmployee(Map<String, String> params)
    {
        return String.format("/employees/login?username=%s&password=%s",
                params.get("username"),
                params.get("password"));
    }
}
