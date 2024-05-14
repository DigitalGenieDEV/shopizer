#!/usr/bin/env sh
basepath=`cd $(dirname $0); cd ../; pwd`
dockerbasepath="$basepath/sm-shop"
targetpath="$dockerbasepath/target"
pompath="$basepath/pom.xml"

mvn clean package -Dmaven.test.skip=true -f $pompath
