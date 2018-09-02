package com.celestialapps.contactfilter.config.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.util.SocketUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collections;

import java.io.Reader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Collections;



import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

@Profile("default")
@Configuration
public class DefaultDataSourceConfig {

    private final EmbeddedDataSourceProperties embeddedDataSourceProperties;

    @Autowired
    public DefaultDataSourceConfig(EmbeddedDataSourceProperties embeddedDataSourceProperties) {
        this.embeddedDataSourceProperties = embeddedDataSourceProperties;
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    @Primary
    public DataSource dataSource() throws IOException {
        new EmbeddedPostgres().start(EmbeddedPostgres.cachedRuntimeConfig(
                Paths.get(System.getProperty("java.io.tmpdir"), "pgembed")),
                "localhost",
                5432,
                "my-db",
                "my-db",
                "my-db",
                Collections.emptyList());

        return embeddedDataSourceProperties.initializeDataSourceBuilder().build();
    }
}