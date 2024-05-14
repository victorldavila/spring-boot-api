package com.finapp.api.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Component
class KeyUtils(
    private val environment: Environment
) {
    @Value("\${access-token.private}")
    private val accessTokenPrivateKeyPath: String? = null

    @Value("\${access-token.public}")
    private val accessTokenPublicKeyPath: String? = null

    @Value("\${refresh-token.private}")
    private val refreshTokenPrivateKeyPath: String? = null

    @Value("\${refresh-token.public}")
    private val refreshTokenPublicKeyPath: String? = null

    private var _accessTokenKeyPair: KeyPair? = null
    private var _refreshTokenKeyPair: KeyPair? = null

    private val accessTokenKeyPair: KeyPair? get() {
        return _accessTokenKeyPair ?: getKeyPair(accessTokenPublicKeyPath, accessTokenPrivateKeyPath)?.apply {
            _accessTokenKeyPair = this
        }
    }

    private val refreshTokenKeyPair: KeyPair? get() {
        return _refreshTokenKeyPair ?: getKeyPair(refreshTokenPublicKeyPath, refreshTokenPrivateKeyPath)?.apply {
            _refreshTokenKeyPair = this
        }
    }

    private fun getKeyPair(publicKeyPath: String?, privateKeyPath: String?): KeyPair? {
        val publicKeyFile = File(publicKeyPath ?: "")
        val privateKeyFile = File(privateKeyPath ?: "")

        if (publicKeyFile.exists() && privateKeyFile.exists()) {
            LOGGER.info("loading keys from file: {}, {}", publicKeyPath, privateKeyPath)

            return loadKeyPair(publicKeyFile, privateKeyFile)
        } else {
            if (environment.activeProfiles.any { it == "prod" }) {
                throw RuntimeException("public and private keys don't exist")
            }
        }

        val directory = File("access-refresh-token-keys")

        if (!directory.exists()) {
            directory.mkdirs()
        }

        return generateKeyPair(publicKeyPath, privateKeyPath)
    }

    private fun loadKeyPair(publicKeyFile: File, privateKeyFile: File): KeyPair? =
        try {
            val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")

            val publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath())
            val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(publicKeyBytes)
            val publicKey: PublicKey = keyFactory.generatePublic(publicKeySpec)

            val privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath())
            val privateKeySpec = PKCS8EncodedKeySpec(privateKeyBytes)
            val privateKey: PrivateKey = keyFactory.generatePrivate(privateKeySpec)

            KeyPair(publicKey, privateKey)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeySpecException) {
            throw RuntimeException(e)
        }

    private fun generateKeyPair(publicKeyPath: String?, privateKeyPath: String?): KeyPair? =
        try {
            LOGGER.info("Generating new public and private keys: {}, {}", publicKeyPath, privateKeyPath)

            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)

            val keyPair = keyPairGenerator.generateKeyPair()

            FileOutputStream(publicKeyPath ?: "").use { fos ->
                val keySpec = X509EncodedKeySpec(keyPair.public.encoded)
                fos.write(keySpec.encoded)
            }

            FileOutputStream(privateKeyPath ?: "").use { fos ->
                val keySpec = PKCS8EncodedKeySpec(keyPair.private.encoded)
                fos.write(keySpec.encoded)
            }

            keyPair
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    fun accessTokenPublicKey(): RSAPublicKey? = accessTokenKeyPair?.public as? RSAPublicKey
    fun accessTokenPrivateKey(): RSAPrivateKey? = accessTokenKeyPair?.private as? RSAPrivateKey
    fun refreshTokenPublicKey(): RSAPublicKey? = refreshTokenKeyPair?.public as? RSAPublicKey
    fun refreshTokenPrivateKey(): RSAPrivateKey? = refreshTokenKeyPair?.private as? RSAPrivateKey

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KeyUtils::class.java)
    }
}