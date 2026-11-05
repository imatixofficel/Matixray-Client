package com.v2ray.ang.ui.main

/** Live proxy traffic shown on the home page: current speed, running totals and a short history. */
data class TrafficUiState(
    val upSpeed: Long = 0L,
    val downSpeed: Long = 0L,
    val upTotal: Long = 0L,
    val downTotal: Long = 0L,
    val upHistory: List<Long> = emptyList(),
    val downHistory: List<Long> = emptyList()
)
