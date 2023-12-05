package common.results;

public record MoveResult<T, R,SideColor>(T successfulResult, R errorResult,SideColor nextTurn, String message) {
}
