
package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductRepository(private val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> =
        productDao.getAllProducts()

    val searchResults = MutableLiveData<List<Product>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProduct(product: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
    }

    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            val result = productDao.findProduct(name)
            searchResults.postValue(result)
        }
    }

    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(name)
        }
    }
}
