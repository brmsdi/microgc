package microgc.component;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableScheduling
public class ReloadComponent {
    private static final String TIME_ZONE = "America/Manaus";

    //@Scheduled(fixedDelay =  6 * 1000)
    @Scheduled(cron = "0 0 0 * * *", zone = TIME_ZONE)
    public void execute()
    {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(getUrl(), null);
    }

    public String getUrl()
    {
        return "http://localhost:8080/data/reload";
    }
}
