package andrewafony.test.domain

sealed interface Result {

    data object Idle : Result

    data object Loading : Result

    data class Error(val error: Throwable) : Result

    data class Success<T>(val value: T) : Result
}