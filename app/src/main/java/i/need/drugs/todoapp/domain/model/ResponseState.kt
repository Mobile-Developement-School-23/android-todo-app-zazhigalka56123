package i.need.drugs.todoapp.domain.model

data class ResponseState<T> (
    val state: State,
    val data: T?
){
    enum class State{STATE_OK, STATE_OFFLINE, STATE_ERROR, STATE_NOT_FOUND}
}