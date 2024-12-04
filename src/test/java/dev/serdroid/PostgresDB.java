package dev.serdroid;

import java.util.Collections;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostgresDB implements QuarkusTestResourceLifecycleManager {

    private static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"))
            .withDatabaseName("pergamon")
            .withUsername("pergamonusr")
            .withPassword("Password")
            .withExposedPorts(5432)
            .withInitScript("init-integration.sql")
            ;

	
	@Override
	public Map<String, String> start() {
		/*
		 * 
quarkus.datasource.username = pergamonusr
quarkus.datasource.password = Password
quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5432/pergamon
		 */
		postgresqlContainer.start();
		System.out.println(" starting postrges container with url = " + postgresqlContainer.getJdbcUrl());
		return Collections.singletonMap("quarkus.datasource.jdbc.url", postgresqlContainer.getJdbcUrl());
	}

	@Override
	public void stop() {
		postgresqlContainer.stop();
	}

}
