package com.sunsandsports.ui

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: UserName,
    @SerializedName("picture") val picture: UserPicture
) : Parcelable

@Parcelize
data class UserName(
    @SerializedName("title") val title: String,
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
): Parcelable

@Parcelize
data class UserPicture(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("thumbnail") val thumbnail: String
) : Parcelable