## How to create "MSK VPC connectivity"?
1. blah
2. 

## How to discover bootstrap brokers programmatically
```
  aws kafka get-bootstrap-brokers --cluster-arn <MSK-CLUSTER-ARN> 
    --query 'BootstrapBrokerStringVpcConnectivitySaslIam' --output text
```

## How to discover VPC bootstrap brokers non programmatically
1. Go to MSK Console → Clusters → Select the target cluster
1. Under Cluster details → Managed VPC connection
1. Copy the VPC connectivity bootstrap broker string (NOT the standard brokers)

## When producer and clients are in two different organization
1. Client subnets MUST be in availability zones that match our MSK cluster's AZ IDs exactly.
2. Ensure clients have subnets in those zones before setting up the Managed VPC Connection.
3. AWS Security groups configured for Kafka traffic
4. SASL Mechnisam : AWS_MSK_IAM for cross-account authentication
5. Authentication : AWS_MSK_IAM


## How to check which AZ a subnet belongs to?
```
aws ec2 describe-subnets --subnet-ids subnet-xxxxxxxxx \
  --query 'Subnets[*].[SubnetId,AvailabilityZoneId,AvailabilityZone]' \
  --output table
```

## Create Missing Subnets

```
aws ec2 create-subnet \
  --vpc-id vpc-xxxxxxxxx \
  --cidr-block 10.0.X.0/24 \
  --availability-zone-id us-east-1
```

## How to create AWS Managed VPC connection

```
aws kafka create-vpc-connection \
  --target-cluster-arn "arn:aws:kafka:us-east-1:ACCOUNT-A:cluster/CLUSTER-NAME/UUID" \
  --authentication '{"Type": "IAM"}' \
  --vpc-id vpc-xxxxxxxxx \
  --client-subnets subnet-xxxxxxxxx subnet-yyyyyyyyy \
  --security-groups sg-xxxxxxxxx
```
