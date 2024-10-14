package com.ingenious.githubapp.data.source.remote

import com.ingenious.githubapp.data.model.response.UserDetailsResponse
import com.ingenious.githubapp.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") perPage: Int = 20,
    ): List<UserResponse>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") login: String,
    ): UserDetailsResponse
}