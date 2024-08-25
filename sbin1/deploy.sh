#!/usr/bin/env sh
basepath=`cd $(dirname $0); cd ../; pwd`
dockerbuild="$basepath/sbin/build.sh"
dockerbasepath="$basepath/sm-shop"
targetpath="$dockerbasepath/target"

sh $dockerbuild

scp -i ckbproduct_key.pem $targetpath/shopizer.jar ubuntu@3.35.47.170:~/devops/sm-shop/target/shopizer.jar
ssh -i ckbproduct_key.pem ubuntu@3.35.47.170 "sh ./devops/startdeploy.sh"

