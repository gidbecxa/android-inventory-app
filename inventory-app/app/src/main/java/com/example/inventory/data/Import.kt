package com.example.inventory.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "import")
data class Import (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    @ColumnInfo (name = "import_name", defaultValue = "imported item") val importName: String,
    @ColumnInfo (name = "import_date") val importDate: String,
    @ColumnInfo (name = "import_price") val importPrice: Double,
    @ColumnInfo (name = "import_quantity") val importQuantity: Int
        )

fun Import.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(importPrice)