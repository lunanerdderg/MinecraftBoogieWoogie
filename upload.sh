#!/bin/bash

cd $HOME/Documents/Programming/eclipse-workspace/TemplateMod/
echo
echo "===================================================="
echo "-= Git fetch =-"
echo
git fetch --all
echo
echo "===================================================="
echo "-= Git status =-"
echo
git status
echo
echo "===================================================="
echo "-= Pushing to Github - '${1:-"Update"}' =-"
echo
git add -A && git commit -a -m "${1:-"Update"}" && git push
git submodule update --remote
echo
echo "===================================================="
echo "-= Git status =-"
echo
git status
echo
echo "===================================================="
echo "-= Success! =-"
echo
cd ~
