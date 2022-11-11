package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.axonframework.eventhandling.TrackingToken;
import org.axonframework.eventhandling.tokenstore.GenericTokenEntry;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.UnableToClaimTokenException;
import org.axonframework.serialization.SerializedObject;
import org.axonframework.serialization.Serializer;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;

import static java.lang.management.ManagementFactory.getRuntimeMXBean;
import static java.time.Clock.systemUTC;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static org.axonframework.common.DateTimeUtils.formatInstant;


@AllArgsConstructor
public class CassandraTokenStore implements TokenStore {

    private final TokenEntryRepository repository;
    private final TokenEntryOwnerRepository ownerRepository;
    private final Serializer serializer;
    private final String nodeId = getRuntimeMXBean().getName();

    @Override
    public void storeToken(TrackingToken trackingToken, @Nonnull String processorName, int segment) throws UnableToClaimTokenException {

        repository.insert(buildTokenEntry(trackingToken, processorName, segment));
        ownerRepository.insert(buildTokenEntryOwner(processorName, segment));
    }

    @Override
    public TrackingToken fetchToken(@Nonnull String processorName, int segment) throws UnableToClaimTokenException {

        return repository.findById(buildPK(processorName, segment))
                .map(this::buildGenericTokenEntry)
                .map(token -> token.getToken(serializer))
                .orElse(null);
    }

    @Override
    public void releaseClaim(@Nonnull String processorName, int segment) {
        ownerRepository.deleteById(buildPKOwner(processorName, segment));
    }

    @Override
    public int[] fetchSegments(@Nonnull String processorName) {

        return repository.findByProcessorName(processorName)
                .mapToInt(token -> token.getId().getSegment())
                .toArray();
    }

    @Override
    public void deleteToken(@Nonnull String processorName, int segment) throws UnableToClaimTokenException {

        String ownerId = buildPKOwner(processorName, segment);

        if (ownerRepository.existsById(ownerId)) {
            repository.deleteById(buildPK(processorName, segment));
            ownerRepository.deleteById(ownerId);
        } else {
            throw new UnableToClaimTokenException("Unable to remove token. It is not owned by " + nodeId);
        }
    }

    @Override
    public boolean requiresExplicitSegmentInitialization() {
        return true;
    }

    private static TokenEntryPK buildPK(String processorName, int segment) {
        return TokenEntryPK.builder().processorName(processorName).segment(segment).build();
    }

    private TokenEntry buildTokenEntry(TrackingToken trackingToken, String processorName, int segment) {

        SerializedObject<byte[]> serializedToken = trackingToken != null ?
                serializer.serialize(trackingToken, byte[].class) : null;

        return TokenEntry.builder()
                .id(buildPK(processorName, segment))
                .tokenEntry(trackingToken != null ? ByteBuffer.wrap(serializedToken.getData()) : null)
                .tokenType(trackingToken != null ? serializedToken.getType().getName() : null)
                .timestamp(now(systemUTC()))
                .build();
    }

    private GenericTokenEntry<byte[]> buildGenericTokenEntry(TokenEntry tokenEntry) {

        return new GenericTokenEntry<>(
                tokenEntry.getTokenEntry() != null ? tokenEntry.getTokenEntry().array() : null,
                tokenEntry.getTokenType(),
                formatInstant(tokenEntry.getTimestamp().toInstant(UTC)),
                null,
                tokenEntry.getId().getProcessorName(),
                tokenEntry.getId().getSegment(),
                byte[].class
        );
    }

    private TokenEntryOwner buildTokenEntryOwner(String processorName, int segment) {
        return TokenEntryOwner.builder().id(buildPKOwner(processorName, segment)).build();
    }

    private String buildPKOwner(String processorName, int segment) {
        return serializer.serialize(
                PKOwner.builder()
                        .processorName(processorName)
                        .segment(segment)
                        .nodeId(nodeId)
                        .build(),
                String.class
        ).getData();
    }

    @Builder
    private record PKOwner(String processorName, int segment, String nodeId) {
    }
}
