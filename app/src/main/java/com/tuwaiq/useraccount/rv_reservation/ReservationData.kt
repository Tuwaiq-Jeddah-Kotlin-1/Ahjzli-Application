package com.tuwaiq.useraccount.rv_reservation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReservationData(
    var storeName:String="",
    var branchName:String="",
    var numberOfTheCustomer:Int=1,
    var maps :String="",
    var userId : String="",
    var userName:String = "",
    var userPhone:String = ""
):Parcelable
