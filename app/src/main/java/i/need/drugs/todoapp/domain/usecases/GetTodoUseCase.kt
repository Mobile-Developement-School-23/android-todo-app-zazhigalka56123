package i.need.drugs.todoapp.domain.usecases

import i.need.drugs.todoapp.domain.repository.MainRepository
import java.util.UUID
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(private val mainRepositoryImpl: MainRepository) {

    suspend operator fun invoke(id: UUID) = mainRepositoryImpl.getTodo(id)
}
