package com.muratcay.todoappepoxy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muratcay.todoappepoxy.database.dao.CategoryEntityDao
import com.muratcay.todoappepoxy.database.dao.ItemEntityDao
import com.muratcay.todoappepoxy.database.entity.CategoryEntity
import com.muratcay.todoappepoxy.database.entity.ItemEntity

@Database(
    entities = [ItemEntity::class, CategoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemEntityDao(): ItemEntityDao
    abstract fun categoryEntityDao(): CategoryEntityDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase != null) {
                return appDatabase!!
            }

            appDatabase = Room
                .databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "to-buy-database"
                )
                .build()
            return appDatabase!!
        }
    }

//    class MIGRATION_1_2 : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `category_entity` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
//        }
//    }

}