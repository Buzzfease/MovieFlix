package top.hanyue.movieflex.base

import com.chad.library.adapter.base.entity.MultiItemEntity

class BaseItem<T>(private val itemType: Int, private val data: T) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }

    fun getData(): T {
        return data
    }

    companion object {
        const val ITEM_MOVIE = 0
        const val ITEM_EPISODE = 1
    }
}