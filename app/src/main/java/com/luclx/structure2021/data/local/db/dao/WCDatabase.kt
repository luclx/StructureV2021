package com.sin.buildingInsights.data.local.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luclx.structure2021.data.local.db.dao.FaultDao
import com.sin.buildingInsights.data.model.bean.broker.PayloadBroker
import com.sin.buildingInsights.data.model.bean.fault.FaultRequest

@Database(
    entities = [WorkOrderRequest::class, FaultRequest::class, InspectionRequest::class, PayloadBroker::class],
    version = 3
)
abstract class WCDatabase : RoomDatabase() {
    abstract fun workOrderDao(): WorkOrderDao

    abstract fun faultDao(): FaultDao

    abstract fun inspectionDao(): InspectionDao

    abstract fun brokerDao(): BrokerDao
}