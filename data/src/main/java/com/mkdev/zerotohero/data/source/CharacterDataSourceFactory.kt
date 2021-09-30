package com.mkdev.zerotohero.data.source

import com.mkdev.zerotohero.data.repository.CharacterCache
import com.mkdev.zerotohero.data.repository.CharacterDataSource
import javax.inject.Inject

class CharacterDataSourceFactory @Inject constructor(
    private val characterCache: CharacterCache,
    private val characterCacheDataSource: CharacterCacheDataSource,
    private val characterRemoteDataSource: CharacterRemoteDataSource
) {
    open suspend fun getDataStore(isCached: Boolean): CharacterDataSource {
        return if (isCached && !characterCache.isExpired()) {
            return getCacheDataSource()
        } else {
            getRemoteDataSource()
        }
    }

    fun getRemoteDataSource(): CharacterDataSource {
        return characterRemoteDataSource
    }

    fun getCacheDataSource(): CharacterDataSource {
        return characterCacheDataSource
    }
}