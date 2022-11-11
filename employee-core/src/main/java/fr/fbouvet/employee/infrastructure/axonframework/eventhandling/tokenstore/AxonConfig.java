package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    public TokenStore myTokenStore(TokenEntryRepository repository,
                                   TokenEntryOwnerRepository ownerRepository,
                                   Serializer serializer) {
        return new CassandraTokenStore(repository, ownerRepository, serializer);
    }
}
