package com.example.androidsocialnetwork.data.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidsocialnetwork.data.entities.UserItem
import javax.inject.Inject


class UsersPagingSource (
    private val networkService: NetworkService,
    ) : PagingSource<Int, UserItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {

        val pageIndex = params.key ?: 1

        return try {

            val users = networkService.getUsers(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = users,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (users.size == params.loadSize) {
                    pageIndex + (params.loadSize / 20)
                } else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {

        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

}