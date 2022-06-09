package com.mpwd2.momomotus.data.entities


data class User(
    val email: String? = null,
    val id: String? = null,
    val score: Int? = null,
    val pseudo: String? = null,
    val image: String? = null,
) {
    constructor() : this(
        email = null,
        id = null,
        score = null,
        pseudo = null,
        image = null
    )
}