package com.ian.coru1

import java.io.Serializable


data class Photo(
    var thumbnail : String?,
    var author : String?,
    var createdAt : String?,
    var likeCount : Int?
):Serializable
