# Source: https://github.com/chrisbanes/tivi/blob/master/release/signing-setup.sh

#!/bin/bash

ENCRYPT_KEY=$1

if [[ ! -z "$ENCRYPT_KEY" ]]; then
  # Decrypt Release key
  openssl aes-256-cbc -md sha256 -d -in release/detectivedroid-release.aes -out release/detectivedroid-release.keystore -k ${ENCRYPT_KEY}

  # Decrypt Play Store key
  openssl aes-256-cbc -md sha256 -d -in release/play-account.aes -out release/play-account.json -k ${ENCRYPT_KEY}
else
  echo "ENCRYPT_KEY is empty"
fi