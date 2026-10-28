# Matixray

A fast and simple proxy client for Android.

## Build and release

Open the **Actions** tab, choose **Build APK** and press **Run workflow**.

- Leave *release_tag* empty for a test build; the APKs appear as artifacts of the run.
- Fill *release_tag* (for example `1.0.1`) to publish a release. Matixray users are offered the update
  automatically, and *release_notes* is the message they see.

Add these repository secrets so every release is signed with the same key (updates need this):
`APP_KEYSTORE_BASE64`, `APP_KEYSTORE_PASSWORD`, `APP_KEYSTORE_ALIAS`, `APP_KEY_PASSWORD`.
