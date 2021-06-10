package my.project.myprojectfinal.todolist.db.todolist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize()
@Entity(tableName = "catatan_harian")
data class Catatanharian(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "title")
    var judul_catatan: String,
    @ColumnInfo(name = "catatan_harian")
    var notesharian: String,
    @ColumnInfo(name = "tempo_millis")
    var rentang_waktu: Long,
    @ColumnInfo(name = "tempo_tanggal")
    var rentang_tanggal: String,
    @ColumnInfo(name = "waktu_dibuat_millis")
    val create_waktu: Long,
    @ColumnInfo(name = "waktu_dibuat")
    val create_string_waktu: String,
    @ColumnInfo(name = "waktu_update")
    var times_update: String,
    @ColumnInfo(name = "update_or_not")
    var title_times_update: String
) : Parcelable