#!/bin/bash

echo "Checking for NPM"
echo " Collection URL: " $COLLECTION_URL

echo "Node Version:   " `node -v`
echo "NPM Version:   " `npm -v`
echo "Old Newman Version:   " `newman --version`

echo "Install newest newman version."
npm install newman --global --no-spin

echo "New Newma Version:   " `newman --version`

newman run $COLLECTION_URL

echo "Complete!"