package br.com.americanas.digital.starwars;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@SpringBootApplication
@EnableWebFlux
public class StarwarsApplication {

	private static final int DURACAO = 1;

	public static void main(String[] args) {
		SpringApplication.run(StarwarsApplication.class, args);
	}

	@Bean
	public WebClient buildClient() {
		return WebClient.create();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Cache<String, Object> cache() {
		return CacheBuilder.newBuilder()
				.expireAfterWrite(DURACAO, TimeUnit.MINUTES)
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())
				.build();
	}

}
