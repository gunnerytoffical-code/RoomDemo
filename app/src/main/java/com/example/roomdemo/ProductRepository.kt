
package com.example.roomdemo

import ProductDao
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

val searchResults = MutableLiveData<List<Product>>()

private val coroutineScope = CoroutineScope(Dispatchers.Main)

private lateinit var productDao: ProductDao

fun initializeDatabase(context: Context) {
    productDao = ProductRoomDatabase
        .getInstance(context)
        .productDao()
}

fun insertProduct(newProduct: Product) {
    coroutineScope.launch(Dispatchers.IO) {
        productDao.insertProduct(newProduct)
    }
}

fun deleteProduct(name: String) {
    coroutineScope.launch(Dispatchers.IO) {
        productDao.deleteProduct(name)
    }
}

fun findProduct(name: String) {
    coroutineScope.launch(Dispatchers.Main) {
        searchResults.value = asyncFind(name).await()
    }
}

private fun asyncFind(name: String) =
    coroutineScope.async(Dispatchers.IO) {
        productDao.findProduct(name)
    }

class ProductRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()
    val searchResults = MutableLiveData<List<Product>>()
}