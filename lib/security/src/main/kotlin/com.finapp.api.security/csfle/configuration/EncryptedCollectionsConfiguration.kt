package com.finapp.api.security.csfle.configuration

import com.finapp.api.user.data.User

/**
 * Information about the encrypted collections in the application.
 * As I need the information in multiple places, I decided to create a configuration class with a static list of
 * the encrypted collections and their information.
 */
object EncryptedCollectionsConfiguration {
    val encryptedEntities: List<EncryptedEntity> = java.util.List.of<EncryptedEntity>(
        EncryptedEntity("Test", "user", User::class.java, "userDEK"),
        //EncryptedEntity("mydb", "companies", CompanyEntity::class.java, "companyDEK")
    )
}