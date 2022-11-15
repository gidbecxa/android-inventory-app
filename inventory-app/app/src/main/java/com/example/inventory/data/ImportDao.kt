package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImportDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (item: Import)

    @Update
    suspend fun update(item: Import)

    @Delete
    suspend fun delete(item: Import)

    @Query("SELECT * FROM import WHERE id = :id")
    fun getItem(id: Int): Flow<Import>

    @Query("SELECT * FROM import ORDER BY import_name ASC")
    fun getItems(): Flow<List<Import>>
}