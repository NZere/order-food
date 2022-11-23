package project.app.team7cafe.Database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import project.app.team7cafe.Database.Database
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import project.app.team7cafe.Model.Order
import java.util.ArrayList

open class Database(context: Context?) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {
    fun carts(): List<Order> {
        val db = readableDatabase
        val qb = SQLiteQueryBuilder()
        val sqlSelect = arrayOf("ID","ProductName", "ProductID", "Quantity", "Price", "Discount")
        val sqlTable = "OrderDetail"
        qb.tables = sqlTable
        val c = qb.query(db, sqlSelect, null, null, null, null, null)
        val result: MutableList<Order> = ArrayList()
        if (c.moveToFirst()) {
            do {
                result.add(
                    Order(
                        c.getInt(c.getColumnIndexOrThrow("ID")),
                        c.getString(c.getColumnIndexOrThrow("ProductID")),
                        c.getString(c.getColumnIndexOrThrow("ProductName")),
                        c.getString(c.getColumnIndexOrThrow("Quantity")),
                        c.getString(c.getColumnIndexOrThrow("Price")),
                        c.getString(c.getColumnIndexOrThrow("Discount"))
                    )
                )
            } while (c.moveToNext())
        }
        return result
    }

    public fun addToCart(order: Order) {
        val db = readableDatabase
        val query = String.format(
            "INSERT INTO OrderDetail(ProductID, ProductName, Quantity, Price, Discount) VALUES('%s','%s','%s','%s','%s');",
            order.productId,
            order.productName,
            order.quantity,
            order.price,
            order.discount
        )
        db.execSQL(query)
    }

    fun cleanCart() {
        val db = readableDatabase
        val query = String.format("DELETE FROM OrderDetail")
        db.execSQL(query)
    }

    fun updateCart(order: Order){
        val db = readableDatabase
        val query = String.format("UPDATE OrderDetail SET Quantity = %s WHERE ID = %d", order.quantity,order.ID)
        db.execSQL(query)

    }

    fun removeFromCart(productId:String){
        val db = readableDatabase
        val query = String.format("DELETE FROM OrderDetail WHERE ProductID = %s", productId)
        db.execSQL(query)
    }


    companion object {
        private const val DB_NAME = "team7DB.db"
        private const val DB_VER = 1
    }
}