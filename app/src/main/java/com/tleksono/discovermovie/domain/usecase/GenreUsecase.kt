package com.tleksono.discovermovie.domain.usecase

import com.tleksono.discovermovie.data.source.repo.AppRepository
import com.tleksono.discovermovie.domain.model.Genre
import com.tleksono.discovermovie.domain.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenreUsecase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend fun getGenre(): State<List<Genre>> {
        return withContext(Dispatchers.IO) {
            appRepository.getGenres()
        }
    }

}
