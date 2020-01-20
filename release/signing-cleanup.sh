# Source: https://github.com/chrisbanes/tivi/blob/master/release/signing-cleanup.sh

#!/bin/bash

# Delete Release key
rm -f release/detectivedroid-release.keystore

# Delete Play Store key
rm -f release/play-account.json