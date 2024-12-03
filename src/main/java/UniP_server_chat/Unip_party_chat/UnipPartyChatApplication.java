package UniP_server_chat.Unip_party_chat;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@EnableMongoRepositories(basePackages = "UniP_server_chat.Unip_party_chat.domain.chatLog.repository.mongorepo")
public class UnipPartyChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnipPartyChatApplication.class, args);
	}

}
