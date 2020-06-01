cf install-plugin cfdev
cf dev start -f <filepath/VERSION.tgz>
cf dev start -f D:\Apps\cf\pcfdev-v1.2.0-windows.tgz

cf target
cat manifest.yaml
cf push

cf marketplace -- would display service and plan
cf create-service mysql free-plan first-db


## Binding service to first-service
cf bind-service first-service first-push-db
cf restart first-push

cf scale first-push -i 2
cf logs first-push

## Delete service

cf delete -f -r first-push
cf delete-service -f first-push-db




* https://katacoda.com/cloudfoundry-tutorials/scenarios/trycf