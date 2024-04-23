package com.finapp.api.security.csfle.configuration

import com.finapp.api.users.data.User

/**
 * Information about the encrypted collections in the application.
 * As I need the information in multiple places, I decided to create a configuration class with a static list of
 * the encrypted collections and their information.
 */
object EncryptedCollectionsConfiguration {
    val encryptedEntities: List<EncryptedEntity> = listOf(
        EncryptedEntity("Test", "users", User::class.java, "userDEK"),
        //EncryptedEntity("Test", "role", Role::class.java, "roleDEK")
    )
}