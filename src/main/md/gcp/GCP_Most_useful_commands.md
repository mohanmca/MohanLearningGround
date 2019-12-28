## Often useful commands
```bash
gcloud compute zones list
gcloud compute images list
gcloud compute instances list
gcloud config set compute/zone asia-southeast1-b
# --maintenance-policy=MIGRATE or Terminate
gcloud compute --project=qwiklabs-gcp-01-ecced1382d7b instances create instance-1 --zone=asia-southeast1-b --machine-type=f1-micro --subnet=default --network-tier=PREMIUM  --service-account=881468636460-compute@developer.gserviceaccount.com --tags=http-server,https-server --image=ubuntu-1804-bionic-v20191211 --image-project=ubuntu-os-cloud --boot-disk-size=10GB --boot-disk-type=pd-standard --boot-disk-device-name=instance-1 --reservation-affinity=any --preemptible

gcloud compute --project=qwiklabs-gcp-01-ecced1382d7b firewall-rules create default-allow-http --direction=INGRESS --priority=1000 --network=default --action=ALLOW --rules=tcp:80 --source-ranges=0.0.0.0/0 --target-tags=http-server

gcloud compute --project=qwiklabs-gcp-01-ecced1382d7b firewall-rules create default-allow-https --direction=INGRESS --priority=1000 --network=default --action=ALLOW --rules=tcp:443 --source-ranges=0.0.0.0/0 --target-tags=https-server

gcloud compute ssh instance-1
```

