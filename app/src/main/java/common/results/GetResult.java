package common.results;

import java.util.Optional;

public record GetResult<T>(Optional<T> successfulResult) {
}

