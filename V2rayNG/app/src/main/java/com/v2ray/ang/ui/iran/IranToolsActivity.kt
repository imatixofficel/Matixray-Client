package com.v2ray.ang.ui.iran

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.v2ray.ang.AppConfig
import com.v2ray.ang.R
import com.v2ray.ang.enums.RoutingType
import com.v2ray.ang.extension.toastSuccess
import com.v2ray.ang.handler.MmkvManager.rememberMmkvBool
import com.v2ray.ang.handler.MmkvManager.rememberMmkvString
import com.v2ray.ang.handler.SettingsChangeManager
import com.v2ray.ang.handler.SettingsManager
import com.v2ray.ang.ui.base.BaseComponentActivity
import com.v2ray.ang.ui.compose.AppTopBar
import com.v2ray.ang.ui.compose.NavigationBarsSpacer
import com.v2ray.ang.ui.compose.PreferenceGroupHeader
import com.v2ray.ang.ui.compose.SettingsListItem
import com.v2ray.ang.ui.compose.SettingsSwitchItem
import com.v2ray.ang.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Settings picked for people on Iranian networks; every option writes a real app preference. */
class IranToolsActivity : BaseComponentActivity() {

    @Composable
    override fun ScreenContent() {
        IranToolsScreen(onBackClick = { finish() })
    }
}

private const val STRENGTH_LIGHT = "light"
private const val STRENGTH_MEDIUM = "medium"
private const val STRENGTH_STRONG = "strong"

@Composable
fun IranToolsScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var routingMode by rememberMmkvString(AppConfig.PREF_IRAN_ROUTING_MODE, "custom")
    var fragmentEnabled by rememberMmkvBool(AppConfig.PREF_FRAGMENT_ENABLED, false)
    var strength by rememberMmkvString(AppConfig.PREF_IRAN_FRAGMENT_STRENGTH, STRENGTH_MEDIUM)
    var fragmentPackets by rememberMmkvString(AppConfig.PREF_FRAGMENT_PACKETS, "tlshello")
    var fragmentLength by rememberMmkvString(AppConfig.PREF_FRAGMENT_LENGTH, "50-100")
    var fragmentInterval by rememberMmkvString(AppConfig.PREF_FRAGMENT_INTERVAL, "10-20")
    var domesticDns by rememberMmkvString(AppConfig.PREF_DOMESTIC_DNS, AppConfig.DNS_DIRECT)
    var remoteDns by rememberMmkvString(AppConfig.PREF_REMOTE_DNS, AppConfig.DNS_PROXY)
    var autoTest by rememberMmkvBool(AppConfig.PREF_AUTO_TEST_AFTER_UPDATE_SUBSCRIPTION, false)
    var autoSort by rememberMmkvBool(AppConfig.PREF_AUTO_SORT_AFTER_TEST, false)
    var autoRemove by rememberMmkvBool(AppConfig.PREF_AUTO_REMOVE_INVALID_AFTER_TEST, false)
    var speedEnabled by rememberMmkvBool(AppConfig.PREF_SPEED_ENABLED, false)

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_iran_tools),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.iran_tools_intro),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            )

            PreferenceGroupHeader(title = stringResource(R.string.iran_group_routing))
            SettingsListItem(
                title = stringResource(R.string.iran_routing_mode),
                entries = listOf(
                    stringResource(R.string.iran_route_custom),
                    stringResource(R.string.iran_route_iran),
                    stringResource(R.string.iran_route_global)
                ),
                values = listOf("custom", "iran", "global"),
                selectedValue = routingMode,
                onSelected = { mode ->
                    routingMode = mode
                    val type = when (mode) {
                        "iran" -> RoutingType.WHITE_IRAN
                        "global" -> RoutingType.GLOBAL
                        else -> null
                    }
                    if (type != null) {
                        scope.launch(Dispatchers.IO) {
                            try {
                                SettingsManager.resetRoutingRulesetsFromPresets(context, type)
                                SettingsChangeManager.makeRestartService()
                                withContext(Dispatchers.Main) {
                                    context.toastSuccess(R.string.toast_success)
                                }
                            } catch (e: Exception) {
                                LogUtil.e(AppConfig.TAG, "Failed to apply the routing preset", e)
                            }
                        }
                    }
                }
            )
            HintText(stringResource(R.string.iran_routing_hint))

            PreferenceGroupHeader(title = stringResource(R.string.iran_group_antifilter))
            SettingsSwitchItem(
                title = stringResource(R.string.iran_fragment_title),
                summary = stringResource(R.string.iran_fragment_summary),
                checked = fragmentEnabled,
                onCheckedChange = { fragmentEnabled = it }
            )
            SettingsListItem(
                title = stringResource(R.string.iran_fragment_strength),
                entries = listOf(
                    stringResource(R.string.iran_strength_light),
                    stringResource(R.string.iran_strength_medium),
                    stringResource(R.string.iran_strength_strong)
                ),
                values = listOf(STRENGTH_LIGHT, STRENGTH_MEDIUM, STRENGTH_STRONG),
                selectedValue = strength,
                enabled = fragmentEnabled,
                onSelected = { value ->
                    strength = value
                    fragmentPackets = "tlshello"
                    when (value) {
                        STRENGTH_LIGHT -> {
                            fragmentLength = "100-200"
                            fragmentInterval = "10-20"
                        }

                        STRENGTH_STRONG -> {
                            fragmentLength = "10-30"
                            fragmentInterval = "10-20"
                        }

                        else -> {
                            fragmentLength = "50-100"
                            fragmentInterval = "10-20"
                        }
                    }
                }
            )

            PreferenceGroupHeader(title = stringResource(R.string.iran_group_dns))
            SettingsListItem(
                title = stringResource(R.string.iran_domestic_dns),
                entries = listOf(
                    stringResource(R.string.iran_dns_auto),
                    stringResource(R.string.iran_dns_shecan),
                    stringResource(R.string.iran_dns_electro),
                    stringResource(R.string.iran_dns_radar)
                ),
                values = listOf(AppConfig.DNS_DIRECT, "178.22.122.100", "78.157.42.100", "10.202.10.10"),
                selectedValue = domesticDns,
                onSelected = { domesticDns = it }
            )
            SettingsListItem(
                title = stringResource(R.string.iran_remote_dns),
                entries = listOf(
                    stringResource(R.string.iran_dns_google),
                    stringResource(R.string.iran_dns_cloudflare),
                    stringResource(R.string.iran_dns_quad9)
                ),
                values = listOf(
                    AppConfig.DNS_PROXY,
                    "https://cloudflare-dns.com/dns-query",
                    "https://dns.quad9.net/dns-query"
                ),
                selectedValue = remoteDns,
                onSelected = { remoteDns = it }
            )
            HintText(stringResource(R.string.iran_dns_hint))

            PreferenceGroupHeader(title = stringResource(R.string.iran_group_configs))
            SettingsSwitchItem(
                title = stringResource(R.string.iran_auto_test),
                summary = stringResource(R.string.iran_auto_test_summary),
                checked = autoTest,
                onCheckedChange = { autoTest = it }
            )
            SettingsSwitchItem(
                title = stringResource(R.string.iran_auto_sort),
                summary = stringResource(R.string.iran_auto_sort_summary),
                checked = autoSort,
                onCheckedChange = { autoSort = it }
            )
            SettingsSwitchItem(
                title = stringResource(R.string.iran_auto_remove),
                summary = stringResource(R.string.iran_auto_remove_summary),
                checked = autoRemove,
                onCheckedChange = { autoRemove = it }
            )

            PreferenceGroupHeader(title = stringResource(R.string.iran_group_connection))
            SettingsSwitchItem(
                title = stringResource(R.string.title_pref_speed_enabled),
                summary = stringResource(R.string.summary_pref_speed_enabled),
                checked = speedEnabled,
                onCheckedChange = { speedEnabled = it }
            )

            NavigationBarsSpacer()
        }
    }
}

@Composable
private fun HintText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    )
}
