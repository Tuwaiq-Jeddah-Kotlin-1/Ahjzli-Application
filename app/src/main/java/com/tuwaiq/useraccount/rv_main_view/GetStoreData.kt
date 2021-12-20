package com.tuwaiq.useraccount.rv_main_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetStoreData(
    var storeName:String="",
    var branchName:String="",
    var branchLocation:String=""
):Parcelable
