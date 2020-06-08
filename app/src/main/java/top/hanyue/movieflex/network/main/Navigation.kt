package top.hanyue.movieflex.network.main

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

/**
 * Created by Buzz on 2020/4/14.
 * Email :lmx2060918@126.com
 */
@Parcelize
@JsonClass(generateAdapter = true)
data class Navigation(
    var code: Int,
    var data: List<Column>,
    var msg: String
):Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Column(
    var category: List<Category>,
    var column_name: String,
    var id: Int,
    var module: Int,
    var search_title: String
):Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Category(
    var id: Int,
    var list: List<CategoryDetail>,
    var name: String,
    var param: String
):Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class CategoryDetail(
    var key: String,
    var name: String
):Parcelable
