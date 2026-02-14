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

## Security Groups are stateful:
1. If inbound traffic is allowed, response traffic is automatically allowed.
1. You do NOT need outbound rules for return traffic.

## How to create AWS Managed VPC connection
```
aws kafka create-vpc-connection \
  --target-cluster-arn "arn:aws:kafka:us-east-1:ACCOUNT-A:cluster/CLUSTER-NAME/UUID" \
  --authentication '{"Type": "IAM"}' \
  --vpc-id vpc-xxxxxxxxx \
  --client-subnets subnet-xxxxxxxxx subnet-yyyyyyyyy \
  --security-groups sg-xxxxxxxxx
```

## What policy required for AWS_MSK_IAM
1. If client/producer connection settings mention SASL_SSL + AWS_MSK_IAM / SASL_IAM
2. if client/producer connection mention SCRAM or client certificates, we don't need this policy.
3. IAM policy for Amazon MSK “IAM access control” or "AWS MSK IAM / SASL_IAM authentication policy"

## Typical client role IAM policy (producer + consumer)
```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "ClusterAccess",
      "Effect": "Allow",
      "Action": [
        "kafka-cluster:Connect",
        "kafka-cluster:DescribeCluster"
      ],
      "Resource": [
        "arn:aws:kafka:us-east-1:111122223333:cluster/MyCluster/abcd1234-0123-abcd-5678-1234abcd-1"
      ]
    },
    {
      "Sid": "TopicAccess",
      "Effect": "Allow",
      "Action": [
        "kafka-cluster:DescribeTopic",
        "kafka-cluster:ReadData",
        "kafka-cluster:WriteData"
      ],
      "Resource": [
        "arn:aws:kafka:us-east-1:111122223333:topic/MyCluster/*"
      ]
    },
    {
      "Sid": "GroupAccess",
      "Effect": "Allow",
      "Action": [
        "kafka-cluster:DescribeGroup",
        "kafka-cluster:AlterGroup"
      ],
      "Resource": [
        "arn:aws:kafka:us-east-1:111122223333:group/MyCluster/*"
      ]
    }
  ]
}
```

## Cross-account access: MSK cluster policy (resource-based)

```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": { "AWS": "123456789012" },
      "Action": [
        "kafka:GetBootstrapBrokers",
        "kafka:DescribeCluster",
        "kafka:DescribeClusterV2",
        "kafka-cluster:*"
      ],
      "Resource": "arn:aws:kafka:us-east-1:111122223333:cluster/testing/de8982fa-8222-4e87-8b20-9bf3cdfa1521-2"
    }
  ]
}
```
