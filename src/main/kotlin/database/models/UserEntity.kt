package com.sproutscout.api.database.models

import com.sproutscout.api.models.models.User


data class UserEntity(
    val id: Int,
    val username: String,
    val email: String,
    val isAdmin: Boolean,
    val passwordHash: String,
)

fun UserEntity.toDomain(): User = User(
    id = id,
    username = username,
    email = email,
)