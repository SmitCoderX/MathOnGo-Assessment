package com.smitcoderx.mathongoassignment.ui.favourite

import com.smitcoderx.mathongoassignment.db.ReciipiieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository @Inject constructor(private val dao: ReciipiieDao) {

   suspend fun getSavedList() = dao.getAllRecipes()

}