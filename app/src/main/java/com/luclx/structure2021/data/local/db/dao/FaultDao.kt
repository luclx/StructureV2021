package com.luclx.structure2021.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sin.buildingInsights.data.model.bean.fault.FaultRequest

@Dao
interface FaultDao {
    @Query("SELECT * FROM fault WHERE isDraft = :isDraft ORDER BY id_index DESC LIMIT 1")
    suspend fun loadFaultRequest(isDraft: Boolean): FaultRequest?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaultRequest(request: FaultRequest): Long

    @Query("DELETE FROM fault WHERE id_index = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM fault WHERE isDraft = :isDraft")
    suspend fun deleteDraft(isDraft: Boolean)
}