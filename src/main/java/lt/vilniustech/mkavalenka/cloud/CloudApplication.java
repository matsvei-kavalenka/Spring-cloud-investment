package lt.vilniustech.mkavalenka.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;


@EnableEurekaServer
@SpringBootApplication
public class CloudApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(CloudApplication.class)
				.run(args);

		WebClient loadBalancedClient = ctx.getBean(WebClient.Builder.class).build();

		for(int i = 1; i <= 10; i++) {
			String response =
					Objects.requireNonNull(loadBalancedClient.get().uri("http://investment/api/xml/hello")
                                    .retrieve().
                                    toEntity(String.class)
                                    .block()).
							getBody();
			System.out.println(response);
		}
	}

}
