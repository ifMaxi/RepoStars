package com.maxidev.repostars.utils

import com.maxidev.repostars.BuildConfig

object ConstantUtils {

    // Remote related constants
    const val BASE_URL = "https://api.github.com/"
    const val CONTENT_TYPE = "application/json"
    const val TIMER_OUT = 15L
    const val API_KEY = BuildConfig.apiKey
    const val AUTHORIZATION = "Authorization"
    const val CACHE_CONTROL = "Cache-Control"
    const val CACHE_MAX_AGE = 2
    const val FILE_CACHE_NAME = "repostars_cache"

    // Url related constants
    const val SEARCH = "search"
    const val REPOSITORIES = "/repositories"

    // Paging related constants
    const val PAGE = 1
    const val PER_PAGE = 10
}