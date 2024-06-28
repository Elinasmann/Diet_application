package com.example.diet_application.db

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.diet_application.MainDao
import java.util.Date

@Database(entities = [User::class, UserResults::class, Product::class, StockProduct::class, Recipe::class,
    ProductsOfRecipe::class, ProductInCart::class, ScheduleOfRecipe::class, Exercise::class, ScheduleOfExercise::class],
    version = 1, exportSchema = true)
@TypeConverters(Converters::class)
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
//                    .createFromAsset("asset.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return value.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time.toLong()
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
    tableName = "user_results",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("user_id", unique = true)
    ]
)
data class UserResults(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Int = 0,
    @ColumnInfo(name = "calories") val calories: Float,
    @ColumnInfo(name = "proteins") val proteins: Float,
    @ColumnInfo(name = "lipids") val lipids: Float,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Float
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
    val calories: Float,
    val proteins: Float,
    val lipids: Float,
    val carbohydrates: Float,
    @ColumnInfo(name = "not_raw") val notRaw: Boolean
)

@Entity(
    tableName = "stock_products",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StockProduct(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    val title: String,
    val description: String = "",
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
    val description: String,
    val calories: Float,
    val proteins: Float,
    val lipids: Float,
    val carbohydrates: Float
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
    @ColumnInfo(name = "measure_amount") val measureAmount: Float,
    @ColumnInfo(name = "measure_title") val measureTitle: String
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
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]
        )
    ],
    indices = [
        Index("user_id", "date")
    ]
)
data class ScheduleOfRecipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "recipe_id") val recipeId: Int = 0,
    @ColumnInfo(name = "product_id") val productId: Int = 0,
    val date: Date
)


@Entity(
    tableName = "exercises"
)
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val type: String,
    val description: String
)
@Entity(
    tableName = "schedule_of_exercises",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"]
        )
    ],
    indices = [
        Index("user_id", "date")
    ]
)
data class ScheduleOfExercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "exercise_id") val recipeId: Int,
    val minutes: Float,
    val date: Date
)