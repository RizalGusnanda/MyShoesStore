package com.dicoding.myshoestore.data

import com.dicoding.myshoestore.model.Shoe
import com.dicoding.myshoestore.model.ShoesData

class ShoeRepository {
    fun getShoes(): List<Shoe> {
        return ShoesData.shoes
    }

    fun searchShoes(query: String): List<Shoe>{
        return ShoesData.shoes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}