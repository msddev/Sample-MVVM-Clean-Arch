package com.mkdev.zerotohero.cache.datasourceImpl

import com.mkdev.zerotohero.cache.dao.CharacterDao
import com.mkdev.zerotohero.cache.mapper.CharacterCacheMapper
import com.mkdev.zerotohero.cache.utils.CachePreferencesHelper
import com.mkdev.zerotohero.data.repository.CharacterCache
import com.mkdev.zerotohero.data.models.CharacterEntity
import javax.inject.Inject

class CharacterCacheImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterCacheMapper: CharacterCacheMapper,
    private val cachePreferencesHelper: CachePreferencesHelper
) : CharacterCache {
    override suspend fun getCharacters(): List<CharacterEntity> =
        characterDao.getCharacters().map { cacheCharacter ->
            characterCacheMapper.mapFromCached(cacheCharacter)
        }

    override suspend fun getCharacter(characterId: Long): CharacterEntity =
        characterCacheMapper.mapFromCached(characterDao.getCharacter(characterId))

    override suspend fun saveCharacters(listCharacters: List<CharacterEntity>) =
        characterDao.addCharacter(
            *listCharacters.map {
                characterCacheMapper.mapToCached(it)
            }.toTypedArray()
        )

    override suspend fun getBookMarkedCharacters(): List<CharacterEntity> =
        characterDao.getBookMarkedCharacters().map { cacheCharacter ->
            characterCacheMapper.mapFromCached(cacheCharacter)
        }

    override suspend fun setCharacterBookmarked(characterId: Long): Int =
        characterDao.bookmarkCharacter(characterId)

    override suspend fun setCharacterUnBookMarked(characterId: Long): Int =
        characterDao.unBookmarkCharacter(characterId)

    override suspend fun isCached(): Boolean =
        characterDao.getCharacters().isNotEmpty()

    override suspend fun setLastCacheTime(lastCache: Long) {
        cachePreferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return cachePreferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}