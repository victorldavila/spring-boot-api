package com.finapp.api.security.csfle.components

import com.finapp.api.security.csfle.service.DataEncryptionKeyService
import org.slf4j.LoggerFactory
import org.springframework.data.spel.spi.EvaluationContextExtension
import org.springframework.data.spel.spi.Function
import org.springframework.stereotype.Component
import java.util.*


/**
 * Will evaluate the SePL expressions in the Entity classes like this: #{mongocrypt.keyId(#target)} and insert
 * the right encryption key for the right collection.
 */
@Component
class EntitySpelEvaluationExtension(
    private val dataEncryptionKeyService: DataEncryptionKeyService
) : EvaluationContextExtension {
    override fun getExtensionId(): String {
        return "mongocrypt"
    }

    override fun getFunctions(): Map<String, Function> {
        try {
            return Collections.singletonMap(
                "keyId", Function(
                    EntitySpelEvaluationExtension::class.java.getMethod("computeKeyId", String::class.java), this
                )
            )
        } catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        }
    }

    fun computeKeyId(target: String?): String? {
        val dek = dataEncryptionKeyService.dataEncryptionKeysB64!![target]
        LOGGER.info("=> Computing dek for target {} => {}", target, dek)
        return dek
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(EntitySpelEvaluationExtension::class.java)
    }
}