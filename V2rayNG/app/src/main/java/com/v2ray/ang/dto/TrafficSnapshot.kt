package com.v2ray.ang.dto

import java.io.Serializable

/** Proxy traffic measured by the service over one interval, in bytes and bytes per second. */
data class TrafficSnapshot(
    val upSpeed: Long,
    val downSpeed: Long,
    val upDelta: Long,
    val downDelta: Long
) : Serializable
