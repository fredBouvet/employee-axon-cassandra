package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

@Table("token_entry")
@Value
@Builder
public class TokenEntry {

    @PrimaryKey
    TokenEntryPK id;

    ByteBuffer tokenEntry;
    String tokenType;
    LocalDateTime timestamp;
}
