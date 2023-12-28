package card.neki.nekicard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication

@SecurityScheme(
    name = "bearerAuth",
    scheme = "bearer",
    bearerFormat = "JWT", 
    type = SecuritySchemeType.HTTP, 
    in = SecuritySchemeIn.HEADER
)

@OpenAPIDefinition(info = @Info(title = "Desafio Nekicard", version = "1", description = "Documentação da API Nekicard para Desafio da Neki"))

public class NekicardApplication {

	public static void main(String[] args) {
		SpringApplication.run(NekicardApplication.class, args);
	}

}
