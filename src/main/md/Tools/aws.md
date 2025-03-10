## AWS Desktop
1. c6a.large 
2. us-east-1
3. aws ec2 describe-instances --profile qa --output text --query 'Reservations[*].Instances[*].State.Name' --filters Name=tag:app_role,Values=dev Name=tag:Name,Values=aws-hostname
4. aws-profile/aws-region

## What is the role of EKS, OIDC and AWS_WEB_IDENTITY_TOKEN_FILE? Use prompt base quiz to learn and use!
1. AWS_WEB_IDENTITY_TOKEN_FILE
2. IRSA
3. sts:AssumeRoleWithWebIdentity
4. AWS_ROLE_ARN and AWS_ROLE_SESSION_NAME
