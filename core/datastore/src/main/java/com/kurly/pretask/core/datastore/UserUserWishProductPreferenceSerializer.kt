package com.kurly.pretask.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * An [androidx.datastore.core.Serializer] for the [UserUserWishProductPreferenceSerializer] proto.
 */
class UserUserWishProductPreferenceSerializer @Inject constructor() : Serializer<UserWishProductPreferences> {
    override val defaultValue: UserWishProductPreferences =
        UserWishProductPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserWishProductPreferences =
        try {
            UserWishProductPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: UserWishProductPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}