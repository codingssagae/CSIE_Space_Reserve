package csieReserve.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(info());
    }

    private Info info(){
        return new Info()
                .title("가톨릭대학교 컴퓨터정보공학부 회의실 예약 Swagger")
                .description("컴퓨터정보공학부 회의실 예약 서비스 통신 API")
                .version("1.0.0");
    }

}
