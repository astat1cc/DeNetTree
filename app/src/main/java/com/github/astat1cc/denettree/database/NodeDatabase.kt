package com.github.astat1cc.denettree.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.astat1cc.denettree.models.Node

@Database(
    version = 1,
    entities = [Node::class]
)
@TypeConverters(TypeConverter::class)
abstract class NodeDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao
}