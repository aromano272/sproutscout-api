package com.sproutscout.api.database

import com.sproutscout.api.database.models.UserEntity
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jetbrains.annotations.Blocking

@RegisterKotlinMapper(UserEntity::class)
interface UserDb {

    @Blocking
    @SqlUpdate("INSERT INTO users (username, email, is_admin, password_hash) VALUES (:username, :email, :isAdmin, :passwordHash)")
    @GetGeneratedKeys
    fun insert(
        @Bind("username") username: String,
        @Bind("email") email: String,
        @Bind("isAdmin") isAdmin: Boolean,
        @Bind("passwordHash") passwordHash: String,
    ): Int

    @Blocking
    @SqlQuery("SELECT * FROM users WHERE id = ANY(:ids)")
    fun getAllByIds(@Bind("ids") ids: List<Int>): List<UserEntity>

    @Blocking
    @SqlQuery("SELECT * FROM users WHERE username = :username")
    fun findByUsername(@Bind("username") username: String): UserEntity?

    @Blocking
    @SqlQuery("SELECT * FROM users WHERE id = :id")
    fun findById(@Bind("id") id: Int): UserEntity?

}