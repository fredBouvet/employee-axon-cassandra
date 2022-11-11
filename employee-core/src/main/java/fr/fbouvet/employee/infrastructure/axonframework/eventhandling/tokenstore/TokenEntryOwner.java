package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("token_entry_owner")
@Value
@Builder
public class TokenEntryOwner {

    @PrimaryKey
    String id;
}
