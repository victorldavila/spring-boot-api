package com.finapp.api.security.csfle.service

import com.mongodb.reactivestreams.client.MongoClient



interface KeyVaultService {
    fun setupKeyVaultCollection(mongoClient: MongoClient)
}