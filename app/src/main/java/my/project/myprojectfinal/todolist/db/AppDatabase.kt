package my.project.myprojectfinal.db

import android.content.Context
import androidx.room.*
import my.project.myprojectfinal.db.todolist.Catatanharian
import my.project.myprojectfinal.db.todolist.CatatanharianDao

@Database(entities = [Catatanharian::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): CatatanharianDao

    companion object {
        private const val DB_NAME = "CATATANHARIAN_DB"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME)
                        .build()
                }
            }
            return instance
        }
    }
}