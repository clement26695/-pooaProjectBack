#!/bin/sh

tar -czvf ../myseries_back.tar.gz --exclude=./.mvn --exclude=./.idea --exclude=./.vscode --exclude=./.settings --exclude=./TODO.md --exclude=./target .
