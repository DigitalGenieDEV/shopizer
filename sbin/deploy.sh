#!/usr/bin/env sh
basepath=`cd $(dirname $0); cd ../; pwd`
dockerbuild="$basepath/sbin/build.sh"
dockerbasepath="$basepath/sm-shop"
targetpath="$dockerbasepath/target"

sh $dockerbuild

scp -i ckbsvshopizer_key.pem $targetpath/shopizer.jar ec2-user@54.180.87.211:~/devops/sm-shop/target/shopizer.jar
ssh -i ckbsvshopizer_key.pem -t ec2-user@54.180.87.211 "sh ./devops/startdeploy.sh"
