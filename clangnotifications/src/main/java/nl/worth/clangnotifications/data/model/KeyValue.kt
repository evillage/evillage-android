package nl.worth.clangnotifications.data.model

import android.os.Parcel
import android.os.Parcelable

data class KeyValue(val key: String, val value: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(key)
        p0?.writeString(value)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<KeyValue> {
        override fun createFromParcel(parcel: Parcel): KeyValue {
            return KeyValue(parcel)
        }

        override fun newArray(size: Int): Array<KeyValue?> {
            return arrayOfNulls(size)
        }
    }
}