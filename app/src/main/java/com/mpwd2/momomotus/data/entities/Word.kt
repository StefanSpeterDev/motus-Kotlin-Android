package com.mpwd2.momomotus.data.entities

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("WordName")
    val name: String,
    @SerializedName("WordID")
    val wordId: String,
    @SerializedName("Id")
    val id: String
)