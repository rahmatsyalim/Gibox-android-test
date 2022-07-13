/*
 * Created by Muhamad Syafii
 * Monday, 04/04/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.core.data.auth.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gibox.testandroid.core.data.auth.source.remote.network.AuthService
import com.gibox.testandroid.core.data.auth.source.remote.response.DataItem
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class UserListPagingSource(private val service: AuthService): PagingSource<Int, DataItem>() {

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {

        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.dataListUser(position)
            val products = response.data

            LoadResult.Page(
                data = products,
                prevKey = null,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int {
        return STARTING_PAGE_INDEX
    }

}