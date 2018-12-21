## Uploading hosts file to android virtual device

Example hosts file to reach the backend running on the localhost's docker container:

127.0.0.1       localhost
10.0.2.2        payment-init-backend.ftb-local
::1             ip6-localhost

Upload with adb:

    ./adb root && ./adb -s emulator-5554 remount && ./adb -s emulator-5554 push <path-to-the-hosts-file>/hosts /system/etc/hosts
