package com.v2ray.ang.ui.main

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.v2ray.ang.AppConfig
import com.v2ray.ang.R
import com.v2ray.ang.extension.toSpeedString
import com.v2ray.ang.extension.toTrafficString
import com.v2ray.ang.handler.MmkvManager
import com.v2ray.ang.handler.SettingsManager
import com.v2ray.ang.handler.SpeedtestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.Locale
import java.util.concurrent.TimeUnit

private val PingGood = Color(0xFF22C55E)
private val PingOk = Color(0xFFF59E0B)
private val PingBad = Color(0xFFEF4444)

/** What is known about the live connection: where it exits, and how quickly it answers. */
private data class ConnectionInsights(
    val countryCode: String? = null,
    val countryName: String? = null,
    val ip: String? = null,
    val pingMs: Long? = null,
    val loading: Boolean = false,
    val failed: Boolean = false
)

@Composable
fun HomeTab(
    isRunning: Boolean,
    displayText: String,
    selectedGuid: String?,
    traffic: TrafficUiState,
    onNavigate: (MainDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val profile = remember(selectedGuid) {
        selectedGuid?.takeIf { it.isNotEmpty() }?.let { MmkvManager.decodeServerConfig(it) }
    }
    val insights by rememberConnectionInsights(isRunning, selectedGuid)
    val connectedSince = remember(isRunning) {
        MmkvManager.decodeSettingsString(AppConfig.PREF_CONNECTED_SINCE)
            ?.toLongOrNull()
            ?.takeIf { it > 0L }
            ?: System.currentTimeMillis()
    }
    val now by produceState(initialValue = System.currentTimeMillis(), key1 = isRunning) {
        while (isRunning) {
            value = System.currentTimeMillis()
            delay(1000)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        HeroCard(
            isRunning = isRunning,
            insights = insights,
            configName = profile?.remarks?.takeIf { it.isNotBlank() },
            elapsedMillis = (now - connectedSince).coerceAtLeast(0L)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            val pingValue = insights.pingMs
            StatTile(
                iconRes = R.drawable.ic_speed_24dp,
                label = stringResource(R.string.stat_ping),
                value = if (isRunning && pingValue != null) "$pingValue ms" else "—",
                sub = null,
                accent = when {
                    !isRunning || pingValue == null -> MaterialTheme.colorScheme.outline
                    pingValue <= 150L -> PingGood
                    pingValue <= 400L -> PingOk
                    else -> PingBad
                },
                modifier = Modifier.weight(1f)
            )
            StatTile(
                iconRes = R.drawable.ic_arrow_down_24dp,
                label = stringResource(R.string.stat_download),
                value = if (isRunning) traffic.downSpeed.toSpeedString() else "—",
                sub = if (isRunning) traffic.downTotal.toTrafficString() else null,
                accent = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            StatTile(
                iconRes = R.drawable.ic_arrow_up_24dp,
                label = stringResource(R.string.stat_upload),
                value = if (isRunning) traffic.upSpeed.toSpeedString() else "—",
                sub = if (isRunning) traffic.upTotal.toTrafficString() else null,
                accent = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )
        }

        TrafficGraphCard(traffic = traffic, isRunning = isRunning)

        CardColumn {
            val shortcuts = listOf(
                MainDestination.IranTools,
                MainDestination.Routing,
                MainDestination.Subscriptions,
                MainDestination.PerAppProxy,
                MainDestination.UserAssets
            )
            shortcuts.forEachIndexed { index, destination ->
                if (index > 0) CardDivider()
                NavigationRow(destination = destination, accent = true, onNavigate = onNavigate)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun HeroCard(
    isRunning: Boolean,
    insights: ConnectionInsights,
    configName: String?,
    elapsedMillis: Long
) {
    val scheme = MaterialTheme.colorScheme
    val contentColor = if (isRunning) Color.White else scheme.onSurface
    val subColor = if (isRunning) Color.White.copy(alpha = 0.78f) else scheme.onSurfaceVariant
    val shape = RoundedCornerShape(28.dp)
    val backgroundModifier = if (isRunning) {
        Modifier.background(
            brush = Brush.linearGradient(listOf(Color(0xFF7448F0), Color(0xFF2E1A8F))),
            shape = shape
        )
    } else {
        Modifier.background(color = scheme.surface, shape = shape)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .then(backgroundModifier)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (isRunning) PingGood else scheme.outline)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(if (isRunning) R.string.home_status_on else R.string.home_status_off),
                style = MaterialTheme.typography.titleSmall,
                color = contentColor,
                modifier = Modifier.weight(1f)
            )
            if (isRunning) {
                Text(
                    text = formatDuration(elapsedMillis),
                    style = MaterialTheme.typography.titleSmall,
                    color = subColor
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (isRunning) {
            val code = insights.countryCode
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (code != null) flagEmoji(code) else "🌐",
                    fontSize = 46.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = when {
                            insights.countryName != null -> insights.countryName
                            insights.loading -> stringResource(R.string.home_detecting)
                            else -> stringResource(R.string.home_location_unknown)
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = contentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (insights.ip != null) {
                        Text(
                            text = stringResource(R.string.home_exit_ip, insights.ip),
                            style = MaterialTheme.typography.bodySmall,
                            color = subColor,
                            maxLines = 1
                        )
                    }
                }
            }
            if (configName != null) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = configName,
                    style = MaterialTheme.typography.bodySmall,
                    color = subColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(
                text = stringResource(R.string.home_hint_off),
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
            if (configName != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = configName,
                    style = MaterialTheme.typography.bodySmall,
                    color = subColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun StatTile(
    iconRes: Int,
    label: String,
    value: String,
    sub: String?,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
        Text(
            text = value,
            fontSize = 14.sp,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = sub ?: " ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}

@Composable
private fun TrafficGraphCard(traffic: TrafficUiState, isRunning: Boolean) {
    val down = MaterialTheme.colorScheme.primary
    val up = MaterialTheme.colorScheme.tertiary
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.home_live_traffic),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
            LegendDot(color = down, label = stringResource(R.string.stat_download))
            Spacer(modifier = Modifier.width(12.dp))
            LegendDot(color = up, label = stringResource(R.string.stat_upload))
        }
        Spacer(modifier = Modifier.height(12.dp))
        DualSparkline(
            downValues = if (isRunning) traffic.downHistory else emptyList(),
            upValues = if (isRunning) traffic.upHistory else emptyList(),
            downColor = down,
            upColor = up,
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
        )
    }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DualSparkline(
    downValues: List<Long>,
    upValues: List<Long>,
    downColor: Color,
    upColor: Color,
    modifier: Modifier = Modifier
) {
    val guide = MaterialTheme.colorScheme.outlineVariant
    Canvas(modifier = modifier) {
        val maxValue = maxOf(downValues.maxOrNull() ?: 0L, upValues.maxOrNull() ?: 0L, 1L).toFloat()
        // faint guide lines
        for (i in 0..2) {
            val y = size.height * i / 2f
            drawLine(guide, Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
        }

        fun line(values: List<Long>, color: Color, filled: Boolean) {
            if (values.size < 2) return
            val stepX = size.width / (values.size - 1)
            val path = Path()
            values.forEachIndexed { index, value ->
                val x = index * stepX
                val y = size.height - (value / maxValue) * size.height * 0.92f - size.height * 0.04f
                if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            if (filled) {
                val area = Path()
                area.addPath(path)
                area.lineTo(size.width, size.height)
                area.lineTo(0f, size.height)
                area.close()
                drawPath(area, Brush.verticalGradient(listOf(color.copy(alpha = 0.30f), Color.Transparent)))
            }
            drawPath(
                path,
                color,
                style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }

        line(downValues, downColor, filled = true)
        line(upValues, upColor, filled = false)
    }
}

/** Looks up the exit country and keeps the ping fresh while the connection is up. */
@Composable
private fun rememberConnectionInsights(isRunning: Boolean, selectedGuid: String?): State<ConnectionInsights> {
    return produceState(initialValue = ConnectionInsights(), key1 = isRunning, key2 = selectedGuid) {
        if (!isRunning) {
            value = ConnectionInsights()
            return@produceState
        }
        value = ConnectionInsights(loading = true)
        delay(1500)

        var info: SpeedtestManager.RemoteEndpointInfo? = null
        var attempt = 0
        while (attempt < 3 && info == null) {
            info = withContext(Dispatchers.IO) { runCatching { SpeedtestManager.getRemoteIPInfo() }.getOrNull() }
            if (info == null) delay(2000)
            attempt++
        }
        val ping = measurePing()
        val rawCountry = info?.country?.trim().orEmpty()
        val code = rawCountry.takeIf { it.length == 2 && it.all { c -> c.isLetter() } }?.uppercase()
        val name = when {
            code != null -> Locale.Builder().setRegion(code).build().getDisplayCountry(Locale.getDefault())
            rawCountry.isNotEmpty() -> rawCountry
            else -> null
        }
        value = ConnectionInsights(
            countryCode = code,
            countryName = name,
            ip = info?.ipAddress,
            pingMs = ping,
            loading = false,
            failed = info == null
        )
        while (true) {
            delay(20_000)
            value = value.copy(pingMs = measurePing())
        }
    }
}

/** Round trip through the local proxy to the app's delay-test address; the faster of two tries. */
private suspend fun measurePing(): Long? = withContext(Dispatchers.IO) {
    val port = SettingsManager.getHttpPort()
    if (port <= 0) return@withContext null
    val url = MmkvManager.decodeSettingsString(AppConfig.PREF_DELAY_TEST_URL)
        .takeIf { !it.isNullOrBlank() } ?: AppConfig.DELAY_TEST_URL
    val client = OkHttpClient.Builder()
        .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("127.0.0.1", port)))
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()
    var best: Long? = null
    repeat(2) {
        val start = System.nanoTime()
        try {
            val request = Request.Builder().url(url).header("User-Agent", "Matixray").build()
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful || response.code == 204) {
                    val elapsed = (System.nanoTime() - start) / 1_000_000
                    val current = best
                    if (current == null || elapsed < current) best = elapsed
                }
            }
        } catch (ignored: Exception) {
            // a failed try is simply not counted
        }
    }
    best
}

private fun flagEmoji(code: String): String {
    val upper = code.uppercase()
    if (upper.length != 2 || upper.any { it !in 'A'..'Z' }) return "🌐"
    return String(Character.toChars(0x1F1E6 + (upper[0] - 'A'))) +
        String(Character.toChars(0x1F1E6 + (upper[1] - 'A')))
}

private fun formatDuration(millis: Long): String {
    val totalSeconds = millis / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun SettingsTab(
    onNavigate: (MainDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SectionLabel(stringResource(R.string.group_preferences))
        CardColumn {
            val preferences = listOf(
                MainDestination.IranTools,
                MainDestination.Settings,
                MainDestination.Routing,
                MainDestination.Subscriptions,
                MainDestination.PerAppProxy,
                MainDestination.UserAssets
            )
            preferences.forEachIndexed { index, destination ->
                if (index > 0) CardDivider()
                NavigationRow(destination = destination, accent = false, onNavigate = onNavigate)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        SectionLabel(stringResource(R.string.group_tools))
        CardColumn {
            val tools = listOf(
                MainDestination.Logcat,
                MainDestination.BackupRestore,
                MainDestination.About
            )
            tools.forEachIndexed { index, destination ->
                if (index > 0) CardDivider()
                NavigationRow(destination = destination, accent = false, onNavigate = onNavigate)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
    )
}

@Composable
private fun CardColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@Composable
private fun CardDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
private fun InfoRow(iconRes: Int, title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun NavigationRow(
    destination: MainDestination,
    accent: Boolean,
    onNavigate: (MainDestination) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigate(destination) }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(destination.iconRes),
            contentDescription = null,
            tint = if (accent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(destination.labelRes),
            style = MaterialTheme.typography.bodyLarge,
            color = if (accent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(R.drawable.ic_chevron_right_24dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
    }
}
