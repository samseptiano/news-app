package com.samuelseptiano.mvvmroom.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Created by samuel.septiano on 27/10/2025.
 */
object EncryptionUtils {
    private const val KEY_ALIAS = "room_db_key"

    fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        return if (keyStore.containsAlias(KEY_ALIAS)) {
            keyStore.getKey(KEY_ALIAS, null) as SecretKey
        } else {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            keyGenerator.generateKey()
        }
    }
}