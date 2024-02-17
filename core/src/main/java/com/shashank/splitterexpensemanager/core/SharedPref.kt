package com.shashank.splitterexpensemanager.core
import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.security.GeneralSecurityException
import java.security.KeyStore
import javax.inject.Inject

interface SharedPref {
    fun hasKey(pref: String): Boolean
    fun removePref(pref: String)
    fun setValue(key: String, value: Any?)
    fun getValue(key: String, defaultValue: Any?): Any?
}

class DefaultSharedPref @Inject constructor(
    @ApplicationContext val context: Context
) : SharedPref {

    private var mSharedPref: SharedPreferences? = null

    init {
        getSharedPref(0)
    }

    private fun getSharedPref(retryCount: Int): SharedPreferences? {
        if (mSharedPref == null) {
            try {
                val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
                mSharedPref = EncryptedSharedPreferences.create(
                    context,
                    SHARED_PREF,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
            } catch (e: GeneralSecurityException) {
                deleteSharedPreferences()
                if (retryCount < 1) {
                    getSharedPref(retryCount + 1)
                } else {
                    Log.i("SharedPref", e.message ?: "")
                }
            } catch (e: IOException) {
                deleteSharedPreferences()
                if (retryCount < 1) {
                    getSharedPref(retryCount + 1)
                } else {
                    Log.i("SharedPref", e.message ?: "")
                }
            }
        }
        return mSharedPref
    }

    private fun deleteSharedPreferences() {
        try {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            val sharedPrefsFile =
                File(context.filesDir.parent + "/shared_prefs/" + SHARED_PREF + ".xml")

            // Clear the encrypted prefs
            clearSharedPreference(context)

            // Delete the encrypted prefs file
            if (sharedPrefsFile.exists()) {
                val isDeleted = sharedPrefsFile.delete()
                Log.i(
                    "SharedPref",
                    "EncryptedSharedPref: Shared pref file deleted=$isDeleted; path=${sharedPrefsFile.absolutePath}"
                )
            } else {
                Log.i(
                    "SharedPref",
                    "EncryptedSharedPref: Shared pref file non-existent; path=${sharedPrefsFile.absolutePath}"
                )
            }

            // Delete the master key
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            keyStore.deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        } catch (e: FileNotFoundException) {
            Log.i(
                "SharedPref",
                "EncryptedSharedPref:  Error occurred while trying to reset shared pref=${e.message}",
            )
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /*This is to work around any key-mismatches that may happen.*/
    private fun clearSharedPreference(context: Context) {
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE).edit().clear().apply()
    }

    override fun hasKey(pref: String): Boolean {
        return if (mSharedPref != null && !TextUtils.isEmpty(pref)) {
            mSharedPref!!.contains(pref)
        } else {
            false
        }
    }

    override fun removePref(pref: String) {
        mSharedPref?.edit()?.remove(pref)?.apply()
    }

    override fun setValue(key: String, value: Any?) {
        when (value) {
            is String? -> mSharedPref?.edit { it.putString(key, value) }
            is Int -> mSharedPref?.edit { it.putInt(key, value) }
            is Boolean -> mSharedPref?.edit { it.putBoolean(key, value) }
            is Float -> mSharedPref?.edit { it.putFloat(key, value) }
            is Long -> mSharedPref?.edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    override fun getValue(
        key: String,
        defaultValue: Any?
    ): Any? {
        try {
            return when (defaultValue) {
                is Int -> {
                    mSharedPref?.getInt(key, defaultValue)
                }

                is String -> {
                    mSharedPref?.getString(key, defaultValue)
                }

                is Boolean -> {
                    mSharedPref?.getBoolean(key, defaultValue)
                }

                is Long -> {
                    mSharedPref?.getLong(key, defaultValue)
                }

                is Float -> {
                    mSharedPref?.getFloat(key, defaultValue)
                }

                else -> defaultValue
            }
        } catch (e: ClassCastException) {
            Log.i("SharedPref", e.message ?: "")
            return defaultValue
        }
    }

    companion object {
        private const val SHARED_PREF = "PayLaterPref"
    }
}

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
internal operator fun SharedPref.set(key: String, value: Any?) {
    setValue(key, value)
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values
 * if [defaultValue] is not specified
 */
internal inline operator fun <reified T : Any> SharedPref.get(
    key: String,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getValue(key, (defaultValue as? String) ?: String.EMPTY) as T?
        Int::class -> getValue(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getValue(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getValue(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getValue(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}