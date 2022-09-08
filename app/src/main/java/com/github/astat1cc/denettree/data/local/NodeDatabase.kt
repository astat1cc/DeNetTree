package com.github.astat1cc.denettree.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.astat1cc.denettree.data.local.model.NodeDto

@Database(
    version = 1,
    entities = [NodeDto::class]
)
@TypeConverters(TypeConverter::class)
abstract class NodeDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao
}