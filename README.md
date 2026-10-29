# Matixtay Client

یک کلاینت V2Ray برای اندروید، با پشتیبانی از [هسته Xray](https://github.com/XTLS/Xray-core) و [هسته v2fly](https://github.com/v2fly/v2ray-core)

به نام پروردگار

> [!NOTE]
> **Matixtay Client** نسخه ارتقاع‌یافته و بازطراحی‌شده‌ای از پروژه **v2rayNG** است. این برنامه بر پایه هسته V2Ray توسعه یافته و با بهبودهای رابط کاربری، پایداری و عملکرد بهتر ارائه می‌شود. تمامی قابلیت‌های اصلی v2rayNG در این نسخه حفظ شده و امکانات جدیدی نیز به آن افزوده شده است.

[![API](https://img.shields.io/badge/API-24%2B-yellow.svg?style=flat)](https://developer.android.com/about/versions/lollipop)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.4.0-blue.svg)](https://kotlinlang.org)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/imatixofficel/Matixray-Client)](https://github.com/imatixofficel/Matixray-Client/commits/main)
[![GitHub Releases](https://img.shields.io/github/downloads/imatixofficel/Matixray-Client/latest/total?logo=github)](https://github.com/imatixofficel/Matixray-Client/releases)
[![Chat on Telegram](https://img.shields.io/badge/Chat%20on-Telegram-brightgreen.svg)](https://t.me/imatix7)

---

## درباره پروژه / About

**Matixtay Client** یک کلاینت متن‌باز و رایگان برای اندروید است که به شما امکان می‌دهد با استفاده از پروتکل‌های مختلف V2Ray (مانند VMess، VLESS، Trojan، Shadowsocks و ...) به اینترنت آزاد و امن دسترسی داشته باشید.

### ویژگی‌های کلیدی

- ✅ پشتیبانی از هسته **Xray** و **v2fly**
- ✅ پشتیبانی از پروتکل‌های VMess، VLESS، Trojan، Shadowsocks، SOCKS و HTTP
- ✅ رابط کاربری ساده، سریع و کاربرپسند
- ✅ مدیریت چندین پیکربندی و سرور به صورت همزمان
- ✅ پشتیبانی از اشتراک‌گذاری لینک (Subscription)
- ✅ قابلیت تست سرعت و پینگ سرورها
- ✅ پشتیبانی از Geoip و Geosite برای مسیریابی هوشمند
- ✅ اجرا در پس‌زمینه با اتصال پایدار
- ✅ سازگار با اندروید ۷.۰ (API 24) و بالاتر
- ✅ کاملاً متن‌باز و بدون تبلیغات

---

## دانلود / Download

آخرین نسخه را از اینجا دانلود کنید:

Download the latest release here:

[https://github.com/imatixofficel/Matixray-Client/releases](https://github.com/imatixofficel/Matixray-Client/releases)

> [!TIP]
> Matixtay Client نسخه موبایل است. برای نسخه دسکتاپ به v2rayN مراجعه کنید \
> Matixtay Client is the mobile version. For the desktop version, please visit the v2rayN
>
> https://github.com/2dust/v2rayN

---

### Geoip و Geosite

- فایل‌های geoip.dat و geosite.dat در مسیر `Android/data/com.v2ray.ang/files/assets` قرار دارند (مسیر در برخی دستگاه‌های اندروید ممکن است متفاوت باشد)
- قابلیت دانلود نسخه تقویت‌شده را از این [مخزن](https://github.com/Loyalsoldier/v2ray-rules-dat) دریافت می‌کند (توجه: این قابلیت به یک پروکسی فعال نیاز دارد)
- آخرین [لیست دامنه](https://github.com/Loyalsoldier/v2ray-rules-dat) و [لیست IP](https://github.com/Loyalsoldier/geoip) رسمی را می‌توان به صورت دستی وارد کرد
- امکان استفاده از فایل dat شخص ثالث در همان پوشه وجود دارد، مانند [h2y](https://guide.v2fly.org/routing/sitedata.html#%E5%A4%96%E7%BD%AE%E7%9A%84%E5%9F%9F%E5%90%8D%E6%96%87%E4%BB%B6)

اطلاعات بیشتر در [ویکی](https://github.com/imatixofficel/Matixray-Client/wiki) ما

### Geoip and Geosite

- geoip.dat and geosite.dat files are in `Android/data/com.v2ray.ang/files/assets` (path may differ on some Android device)
- download feature will get enhanced version in this [repo](https://github.com/Loyalsoldier/v2ray-rules-dat) (note: it needs a working proxy)
- latest official [domain list](https://github.com/Loyalsoldier/v2ray-rules-dat) and [ip list](https://github.com/Loyalsoldier/geoip) can be imported manually
- possible to use a third-party dat file in the same folder, like [h2y](https://guide.v2fly.org/routing/sitedata.html#%E5%A4%96%E7%BD%AE%E7%9A%84%E5%9F%9F%E5%90%8D%E6%96%87%E4%BB%B6)

More in our [wiki](https://github.com/imatixofficel/Matixray-Client/wiki)

---

## راهنمای نصب و استفاده / Installation & Usage

### پیش‌نیازها

- دستگاه اندروید با نسخه ۷.۰ (API 24) یا بالاتر
- دسترسی به اینترنت
- مجوز VPN برای اتصال

### مراحل نصب

1. فایل APK را از بخش [Releases](https://github.com/imatixofficel/Matixray-Client/releases) دانلود کنید
2. نصب برنامه را از منابع ناشناس در تنظیمات اندروید فعال کنید
3. فایل APK را نصب کنید
4. پس از باز کردن برنامه، مجوز VPN را تأیید کنید
5. لینک اشتراک یا پیکربندی خود را وارد کنید و متصل شوید

---

## راهنمای توسعه / Development guide

### نکته

- پروژه اندروید در پوشه Matixtay Client را می‌توان مستقیماً در Android Studio یا با استفاده از Gradle wrapper کامپایل کرد. اما هسته v2ray داخل aar (احتمالاً) قدیمی است.
- فایل aar را می‌توان از پروژه Golang [AndroidLibV2rayLite](https://github.com/2dust/AndroidLibV2rayLite) یا [AndroidLibXrayLite](https://github.com/2dust/AndroidLibXrayLite) کامپایل کرد. برای شروع سریع، راهنماهای [Go Mobile](https://github.com/golang/go/wiki/Mobile) و [Makefiles for Go Developers](https://tutorialedge.net/golang/makefiles-for-go-developers/) را مطالعه کنید.
- Matixtay Client روی شبیه‌سازهای اندروید اجرا می‌شود. برای WSA، مجوز VPN باید از طریق `appops set [package name] ACTIVATE_VPN allow` اعطا شود.

### Note

- Android project under the Matixtay Client folder can be compiled directly in Android Studio, or using the Gradle wrapper. But the v2ray core inside the aar is (probably) outdated.
- The aar can be compiled from the Golang project [AndroidLibV2rayLite](https://github.com/2dust/AndroidLibV2rayLite) or [AndroidLibXrayLite](https://github.com/2dust/AndroidLibXrayLite). For a quick start, read the guides for [Go Mobile](https://github.com/golang/go/wiki/Mobile) and [Makefiles for Go Developers](https://tutorialedge.net/golang/makefiles-for-go-developers/).
- Matixtay Client can run on Android Emulators. For WSA, VPN permission needs to be granted via `appops set [package name] ACTIVATE_VPN allow`.

---

## سوالات متداول / FAQ

**آیا Matixtay Client رایگان است؟**
بله، این برنامه کاملاً رایگان و متن‌باز است.

**آیا برنامه اطلاعات من را جمع‌آوری می‌کند؟**
خیر، Matixtay Client هیچ‌گونه اطلاعات شخصی کاربران را جمع‌آوری یا ارسال نمی‌کند.

**آیا نیاز به روت دارد؟**
خیر، برنامه از طریق API رسمی VPN اندروید کار می‌کند و نیازی به روت ندارد.

**چگونه سرور خود را اضافه کنم؟**
از منوی برنامه گزینه «افزودن پیکربندی» را انتخاب کنید یا لینک اشتراک خود را وارد کنید.

---

## تأیید امضای GPG / GPG Verification

فایل‌های انتشار با GPG امضا شده‌اند تا اصالت و یکپارچگی آن‌ها تأیید شود و از هک شدن توسط آینه‌ها، اپراتورها یا CDN جلوگیری شود.

Release files are signed with GPG to verify authenticity and integrity, helping prevent mirror, ISP, or CDN hijacking.

### اثر انگشت / Fingerprint

```text
7694 5E9F 3E9A 168F 8070 F195 805D 661C
134D FAF6 8903 C199 463C 31E5 AE90 3AE0
```

---

## مشارکت / Contributing

از مشارکت شما در توسعه این پروژه استقبال می‌کنیم. برای گزارش باگ یا ارائه پیشنهاد، از بخش [Issues](https://github.com/imatixofficel/Matixray-Client/issues) استفاده کنید.

---

## مجوز / License

این پروژه تحت مجوز **GPL-3.0** منتشر شده است. برای اطلاعات بیشتر فایل LICENSE را مطالعه کنید.

---

## جامعه / Community

گروه تلگرام / Telegram Group：

[https://t.me/imatix7](https://t.me/imatix7)

---

## تشکر و قدردانی / Acknowledgements

- از تیم [v2rayNG](https://github.com/2dust/v2rayNG) برای پایه‌گذاری این پروژه
- از تیم [Xray-core](https://github.com/XTLS/Xray-core) و [v2fly](https://github.com/v2fly/v2ray-core)
- از تمامی مشارکت‌کنندگان و کاربران وفادار
