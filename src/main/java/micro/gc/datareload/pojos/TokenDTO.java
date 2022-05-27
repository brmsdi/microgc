package micro.gc.datareload.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    String type;
    String token;
}