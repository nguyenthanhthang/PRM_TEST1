package com.example.thangnt.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Item(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var subtitle: String? = null,  // mô tả hoặc số điện thoại
    var price: Int? = null,        // nếu làm đồ ăn/đồ uống
    var imageRes: Int? = null
) : Parcelable
