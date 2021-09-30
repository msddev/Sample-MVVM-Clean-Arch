package com.mkdev.zerotohero.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mkdev.zerotohero.cache.dao.CharacterDao
import com.mkdev.zerotohero.cache.models.CharacterCacheEntity
import com.mkdev.zerotohero.cache.models.CharacterLocationCacheEntity
import com.mkdev.zerotohero.cache.utils.Migrations
import com.mkdev.zerotohero.cache.utils.CacheConstants
import javax.inject.Inject

@Database(
    entities = [CharacterCacheEntity::class, CharacterLocationCacheEntity::class],
    version = Migrations.DB_VERSION,
    exportSchema = false
)
abstract class CharactersDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedCharacterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getInstance(context: Context): CharactersDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CharactersDatabase::class.java,
            CacheConstants.DB_NAME
        ).build()
    }
}
