package com.saba.gitrepositoriesdisplay.data.model

data class Verification(
    val payload: Any,
    val reason: String,
    val signature: Any,
    val verified: Boolean
)