package com.example.diet_application

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class, Product::class, StockProduct::class, Recipe::class, ProductsOfRecipe::class, ProductInCart::class, ScheduleOfRecipe::class],
    version = 1, exportSchema = true)
abstract class MainDatabase : RoomDatabase() {

    abstract fun getDao(): MainDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the same time.
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "diet_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


@Entity(
    tableName = "users",
    indices = [
        Index("login", unique = true)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val login: String,
    val password: String,
    val gender: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    @ColumnInfo(name = "physical_activity_ratio") val physicalActivityRatio: Float,
    @ColumnInfo(name = "do_exercises") val doExercises: Boolean = false
)


@Entity(
    tableName = "products",
    indices = [
        Index("title", unique = true)
    ]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    @ColumnInfo(name = "amount_of_days") val amountOfDays: Int? = 0,
    val measure: String?,
    val calories: Int? = 0,
    val proteins: Int? = 0,
    val lipids: Int? = 0,
    val carbohydrates: Int? = 0
)

@Entity(
    tableName = "stock_products"
)
data class StockProduct(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String?,
    @ColumnInfo(name = "expiration_date") val expirationDate: String
)


@Entity(
    tableName = "recipes",
    indices = [
        Index("title", unique = true)
    ]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String?,
    val calories: Int? = 0,
    val proteins: Int? = 0,
    val lipids: Int? = 0,
    val carbohydrates: Int? = 0
)

@Entity(
    tableName = "products_of_recipe",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["product_id", "recipe_id"]
)
data class ProductsOfRecipe(
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    @ColumnInfo(name = "measure_amount") val measureAmount: Int
)


@Entity(
    tableName = "products_in_cart",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]
        )
    ],
    indices = [
        Index("user_id")
    ]
)
data class ProductInCart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "measure_amount") val measureAmount: Int,
    @ColumnInfo(name = "check_buy") val checkBuy: Boolean = false
)

@Entity(
    tableName = "schedule_of_recipes",
    foreignKeys = [
        ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"]
        )
    ],
    indices = [
        Index("user_id", "date")
    ]
)
data class ScheduleOfRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "recipe_id") val recipeId: Int,
    val date: String
)
