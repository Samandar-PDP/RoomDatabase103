package uz.digital.roomdatabase103.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserTable ORDER BY id DESC")
    fun getAllUsers(): List<UserEntity>

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("UPDATE UserTable SET name = :name WHERE id = :id")
    fun updateUserName(id: Int, name: String) // aynan ismni yangilash

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("DELETE FROM UserTable WHERE id = :id")
    fun deleteById(id: Int) // aynan id si bo'yicha o'chiradi

    @Query("DELETE FROM UserTable")
    fun clearUserTable()

    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUserById(id: Int): UserEntity // 1 dona olish
}