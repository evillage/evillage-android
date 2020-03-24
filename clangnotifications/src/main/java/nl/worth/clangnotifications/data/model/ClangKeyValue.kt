package nl.worth.clangnotifications.data.model

import android.os.Parcel
import android.os.Parcelable

data class ClangKeyValue(val key: String, val value: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(key)
        p0?.writeString(value)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ClangKeyValue> {
        override fun createFromParcel(parcel: Parcel): ClangKeyValue {
            return ClangKeyValue(parcel)
        }

        override fun newArray(size: Int): Array<ClangKeyValue?> {
            return arrayOfNulls(size)
        }
    }
}