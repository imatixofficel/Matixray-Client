# Matixtay Client

<div align="center">

### In the Name of Allah, the Most Gracious, the Most Merciful

</div>

<p align="center">
  <img src="https://github.com/imatixofficel/Matixray-Client/blob/main/fastlane/metadata/android/en-US/images/icon.png" alt="Matixtay Client Icon" width="250" height="250">
</p>

<div align="center">

**A V2Ray client for Android, supporting [Xray core](https://github.com/XTLS/Xray-core) and [v2fly core](https://github.com/v2fly/v2ray-core)**

</div>

<div align="center">

**🌐 Language / زبان:** [English](README.md) | [فارسی](README-fa.md)

</div>

> [!NOTE]
> **Matixtay Client** is an upgraded and redesigned version of the **v2rayNG** project. Built on the V2Ray core, it offers an improved user interface, better stability, and enhanced performance. All core features of v2rayNG are preserved, with new capabilities added on top.

<div align="center">

[![API](https://img.shields.io/badge/API-24%2B-yellow.svg?style=flat)](https://developer.android.com/about/versions/lollipop)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.4.0-blue.svg)](https://kotlinlang.org)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/imatixofficel/Matixray-Client)](https://github.com/imatixofficel/Matixray-Client/commits/main)
[![GitHub Releases](https://img.shields.io/github/downloads/imatixofficel/Matixray-Client/latest/total?logo=github)](https://github.com/imatixofficel/Matixray-Client/releases)
[![Chat on Telegram](https://img.shields.io/badge/Chat%20on-Telegram-brightgreen.svg)](https://t.me/imatix7)

</div>

---

## Download

<table align="center">
<tr>
<td align="center" width="50%">

### 📱 Android

<img src="https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android" alt="Android"/>

<br><br>

**[⬇️ Download for Android](https://github.com/imatixofficel/Matixray-Client/releases)**

<br>

Compatible with Android 7.0 (API 24) and above

</td>
<td align="center" width="50%">

### 💻 Windows (Desktop)

<img src="https://img.shields.io/badge/Platform-Windows-blue?style=for-the-badge&logo=windows" alt="Windows"/>

<br><br>

**[⬇️ Download for Windows](https://github.com/imatixofficel/Matixry-windosw)**

<br>

Compatible with Windows 10 and above

</td>
</tr>
</table>

> [!TIP]
> Matixtay Client is available for both **Android** and **Windows**. Choose the version that suits your device.

---

## About

**Matixtay Client** is a free and open-source multi-platform client that lets you access a free and secure internet using various V2Ray protocols (VMess, VLESS, Trojan, Shadowsocks, and more).

### Key Features

- ✅ Support for **Xray** and **v2fly** cores
- ✅ Support for VMess, VLESS, Trojan, Shadowsocks, SOCKS, and HTTP protocols
- ✅ Simple, fast, and user-friendly interface
- ✅ Manage multiple configurations and servers simultaneously
- ✅ Subscription link support
- ✅ Server speed and ping testing
- ✅ Geoip and Geosite support for smart routing
- ✅ Background execution with a stable connection
- ✅ Compatible with Android 7.0 (API 24) and above
- ✅ Available for both Android and Windows
- ✅ Fully open-source and ad-free

---

## Installation & Usage

### Prerequisites

- Android device running version 7.0 (API 24) or higher — or a Windows PC
- Internet access
- VPN permission for connection (Android)

### Installation Steps

1. Download the appropriate version from the links above
2. Enable installation from unknown sources (Android) or run the installer (Windows)
3. Install the application
4. Open the app and grant VPN permission (Android)
5. Add your subscription link or configuration and connect

---

### Geoip and Geosite

- geoip.dat and geosite.dat files are in `Android/data/com.v2ray.ang/files/assets` (path may differ on some Android device)
- download feature will get enhanced version in this [repo](https://github.com/Loyalsoldier/v2ray-rules-dat) (note: it needs a working proxy)
- latest official [domain list](https://github.com/Loyalsoldier/v2ray-rules-dat) and [ip list](https://github.com/Loyalsoldier/geoip) can be imported manually
- possible to use a third-party dat file in the same folder, like [h2y](https://guide.v2fly.org/routing/sitedata.html#%E5%A4%96%E7%BD%AE%E7%9A%84%E5%9F%9F%E5%90%8D%E6%96%87%E4%BB%B6)

More in our [wiki](https://github.com/imatixofficel/Matixray-Client/wiki)

---

## Development Guide

### Note

- The Android project under the Matixtay Client folder can be compiled directly in Android Studio, or using the Gradle wrapper. But the v2ray core inside the aar is (probably) outdated.
- The aar can be compiled from the Golang project [AndroidLibV2rayLite](https://github.com/2dust/AndroidLibV2rayLite) or [AndroidLibXrayLite](https://github.com/2dust/AndroidLibXrayLite). For a quick start, read the guides for [Go Mobile](https://github.com/golang/go/wiki/Mobile) and [Makefiles for Go Developers](https://tutorialedge.net/golang/makefiles-for-go-developers/).
- Matixtay Client can run on Android Emulators. For WSA, VPN permission needs to be granted via `appops set [package name] ACTIVATE_VPN allow`.

---

## FAQ

**Is Matixtay Client free?**
Yes, this application is completely free and open-source.

**Does the app collect my data?**
No, Matixtay Client does not collect or transmit any personal user data.

**Does it require root?**
No, the app works through the official Android VPN API and does not require root.

**Is there a Windows version?**
Yes! You can download the Windows desktop version from [here](https://github.com/imatixofficel/Matixry-windosw).

**How do I add my own server?**
Select "Add Configuration" from the app menu or enter your subscription link.

---

## GPG Verification

Release files are signed with GPG to verify authenticity and integrity, helping prevent mirror, ISP, or CDN hijacking.

### Fingerprint

```text
7694 5E9F 3E9A 168F 8070 F195 805D 661C
134D FAF6 8903 C199 463C 31E5 AE90 3AE0
```

---

## Contributing

We welcome your contributions to this project. To report bugs or submit suggestions, please use the [Issues](https://github.com/imatixofficel/Matixray-Client/issues) section.

---

## License

This project is released under the **GPL-3.0** license. See the LICENSE file for more information.

---

## Community

<div align="center">

### [📢 Join Our Telegram Group](https://t.me/imatix7)

</div>

---

## Acknowledgements

- The [v2rayNG](https://github.com/2dust/v2rayNG) team for laying the foundation of this project
- The [Xray-core](https://github.com/XTLS/Xray-core) and [v2fly](https://github.com/v2fly/v2ray-core) teams
- All contributors and loyal users

---

<div align="center">


</div>
