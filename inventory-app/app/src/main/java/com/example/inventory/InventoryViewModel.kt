package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao): ViewModel() {

    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData() //this is used for the list of inventory items on front page

    fun retrieveItem(id: Int): LiveData<Item> { //this retrieves the item (it's id) from the inventory list (above)
        return itemDao.getItem(id).asLiveData()
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch { itemDao.insert(item) } //a suspend fun can only be called from a coroutine or another suspend fun
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    } //I might have to implement the null entry here, i.e. if a field is empty or @ isEntryValid() below...

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem) //called the insertItem() above, without the viewModelScope.launch here...
    }

    //I might have to implement the null entry here, i.e. if a field is empty
    //I'll simply have to check each column with isBlank() independently of one another
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            //Decrease quantity by 1
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    //to use the update() function from the itemDao
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int, //these are merely parameters to take in. Don't be confused
        itemName: String, //these are merely parameters to take in. Don't be confused
        itemPrice: String, //these are merely parameters to take in. Don't be confused
        itemCount: String //these are merely parameters to take in. Don't be confused... String because user input is converted to String
    ): Item {
        return Item(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    /**
     * this function passes its information/details to fun getUpdatedItemEntry() above
     * ... which in turn processes them for them Item Dao... and hence, the database
     */
    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem) //updateItem() called here is the private fun updateItem() above
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}