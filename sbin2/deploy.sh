#!/usr/bin/env sh
basepath=`cd $(dirname $0); cd ../; pwd`
dockerbuild="$basepath/sbin/build.sh"
dockerbasepath="$basepath/sm-shop"
targetpath="$dockerbasepath/target"

sh $dockerbuild

scp -i ckbproduct2_key.pem $targetpath/shopizer.jar ubuntu@52.79.223.118:~/devops/sm-shop/target/shopizer.jar
ssh -i ckbproduct2_key.pem ubuntu@52.79.223.118 "sh ./devops/startdeploy.sh"
