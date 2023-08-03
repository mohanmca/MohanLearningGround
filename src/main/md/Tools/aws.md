## AWS Desktop
1. c6a.large 
2. us-east-1
3. aws ec2 describe-instances --profile qa --output text --query 'Reservations[*].Instances[*].State.Name' --filters Name=tag:app_role,Values=dev Name=tag:Name,Values=aws-hostname
4. aws-profile/aws-region
