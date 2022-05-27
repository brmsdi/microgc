package micro.gc.datareload;

import lombok.extern.slf4j.Slf4j;
import micro.gc.datareload.component.Authentication;
import micro.gc.datareload.component.ReloadComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableScheduling
@Slf4j
public class Execute {
    private static final String TIME_ZONE = "America/Manaus";

    @Autowired
    private Authentication authentication;

    @Autowired
    private ReloadComponent reloadComponent;

    @Scheduled(cron = "0 50 21 * * *", zone = TIME_ZONE)
    private void init()
    {
        authentication.prepareAuth().subscribe(tokenDTO -> {
            log.info("Authentication ok");
            Mono<String> mono = reloadComponent.executeReload(tokenDTO,"INSERT_YES");
            mono.subscribe(log::info);
        });
        log.info("...");
    }
}
