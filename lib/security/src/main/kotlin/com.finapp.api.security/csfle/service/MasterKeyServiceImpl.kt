package com.finapp.api.security.csfle.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.security.SecureRandom


/**
 * Master Key service.
 * Responsible for the retrieval of an existing Master Key in the local file system if it exists.
 * Responsible for generating and saving a new Master Key to a local file if necessary.
 * It's not recommended to store a Master Key in the local file system in production.
 * [MongoDB KMS provider documentation](https://www.mongodb.com/docs/manual/core/csfle/reference/kms-providers/#supported-key-management-services)
 */
@Service
class MasterKeyServiceImpl : MasterKeyService {

    @Value("\${mongodb.master.key.file.path}") private val masterKeyFileName: String? = null

    override fun generateNewOrRetrieveMasterKeyFromFile(): ByteArray {
        var masterKey = ByteArray(SIZE_MASTER_KEY)

        try {
            masterKeyFileName?.let {
                retrieveMasterKeyFromFile(it, masterKey)

                LOGGER.info("=> An existing Master Key was found in file {}.", it)
            }
        } catch (e: IOException) {
            masterKeyFileName?.let {
                masterKey = generateMasterKey()
                saveMasterKeyToFile(it, masterKey)

                LOGGER.info("=> A new Master Key has been generated and saved to file {}.", it)
            }
        }

        return masterKey
    }

    private fun retrieveMasterKeyFromFile(filename: String, masterKey: ByteArray) {
        FileInputStream(filename).use { fis ->
            fis.read(masterKey, 0, SIZE_MASTER_KEY)
        }
    }

    private fun generateMasterKey(): ByteArray {
        return ByteArray(SIZE_MASTER_KEY).apply {
            SECURE_RANDOM.nextBytes(this)
        }
    }

    private fun saveMasterKeyToFile(filename: String, masterKey: ByteArray) {
        try {
            FileOutputStream(filename).use { fos ->
                fos.write(masterKey)
            }
        } catch (e: IOException) {
            LOGGER.error("=> Couldn't save the Master Key to file {}.", filename)
            LOGGER.error("=> Error message: {}", e.message, e)
        }
    }

    companion object {
        private const val SIZE_MASTER_KEY = 96

        private val SECURE_RANDOM = SecureRandom()

        private val LOGGER = LoggerFactory.getLogger(MasterKeyServiceImpl::class.java)
    }
}