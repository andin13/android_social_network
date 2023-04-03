package com.example.androidsocialnetwork.data.services

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import com.example.androidsocialnetwork.data.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(retrofit: Retrofit) {

    private val retrofitService = retrofit.create(RetrofitNetworkService::class.java)

    suspend fun authMe(): User? {

        val response = retrofitService.authMe().awaitResponse()

        return response.body()?.data
    }

    suspend fun login(loginData: LoginData): UserID? {

        val response = retrofitService.login(loginData).awaitResponse()

        return response.body()?.data
    }

    suspend fun logout() {
        retrofitService.logout().awaitResponse()
    }

    suspend fun getUsers(page: Int, count: Int): List<UserItem> {

        val response = retrofitService.getUsers(page, count).awaitResponse()

        return response.body()?.items?: emptyList()
    }

    suspend fun getProfile(userId: Int): Profile? {

        val response = retrofitService.getProfile(userId.toString()).awaitResponse()

        return response.body()
    }

    fun getPagedUsers(): Flow<PagingData<UserItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 20,
                initialLoadSize = 60,
                //jumpThreshold =

            ),
            pagingSourceFactory = { UsersPagingSource(this) }
        ).flow
    }

}
