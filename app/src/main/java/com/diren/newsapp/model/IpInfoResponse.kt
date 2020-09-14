package com.diren.newsapp.model

import com.google.gson.annotations.SerializedName

data class IpInfoResponse(
    @SerializedName("status") val status: String,
    @SerializedName("country") val country: String,
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("city") val city: String,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("isp") val isp: String,
    @SerializedName("org") val org: String,
    @SerializedName("query") val query: String
)