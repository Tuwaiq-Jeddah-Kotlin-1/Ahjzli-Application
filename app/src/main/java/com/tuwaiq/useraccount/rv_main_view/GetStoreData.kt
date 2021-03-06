package com.tuwaiq.useraccount.rv_main_view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetStoreData(
    var idOwner :String ="",
    var storeName:String="",
    var branchName:String="",
    var branchLocation:String="",
   var maxPeople:Int = 1
):Parcelable
