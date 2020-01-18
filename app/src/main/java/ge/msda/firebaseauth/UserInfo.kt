package ge.msda.firebaseauth


import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo(
    val name: String = "",
    val mobile: String? = "",
    val mail: String?="",
    val address: String?=""

)