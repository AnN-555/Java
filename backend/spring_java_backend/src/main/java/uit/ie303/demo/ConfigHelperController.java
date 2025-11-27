package uit.ie303.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/config")
public class ConfigHelperController {
    
    @Value("${GG_MAP_KEY}")
    private String gg_api_k;

    @GetMapping("/gg/key")
    public String getGGKey(){
        return this.gg_api_k;
    }

    
}
