package com.yasin.rxjavalearn.nestedFlatMap

import com.google.gson.annotations.SerializedName

data class Post(@SerializedName("id")
                val id: Int = 0,
                @SerializedName("title")
                val title: String = "",
                @SerializedName("body")
                val body: String = "",
                @SerializedName("userId")
                val userId: Int = 0)