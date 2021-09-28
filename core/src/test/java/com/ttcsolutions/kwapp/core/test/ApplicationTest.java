package com.ttcsolutions.kwapp.core.test;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public abstract class ApplicationTest {
    public static DockerComposeContainer<?> environment = new DockerComposeContainer<>(new File("config/compose-test.yml"));

    static {
        environment.waitingFor("postgres", Wait.defaultWaitStrategy()).start();
        environment.waitingFor("redis", Wait.defaultWaitStrategy()).start();
    }
}
