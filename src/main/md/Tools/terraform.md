## Terraform
1. terraform init - Initializes the working directory containing the Terraform configuration files. It downloads necessary plugins and providers.
1. terraform init -upgrade #to_download new provider
  
2. terraform fmt
3. terraform validae - Validates the syntax and configuration of the Terraform files.
4. terraform plan - dry-run
5. terraform apply -auto-aprove
6. terraform show - Shows the current state or plan in a human-readable format.
7. terraform import - Imports existing infrastructure into your Terraform state.
8. terraform destroy - Destroys the Terraform-managed infrastructure. It undoes all the applied changes.

## How to generate existing infra as code
1. To export existing infrastructure into Terraform code, you’ll use a combination of Terraform’s import functionality and manually defining the configuration files (since Terraform doesn't auto-generate configuration files from existing resources). 
2. create placeholder
3. terraform import

## Terraform variables
1. It auotmatically loads all the files with extension of "*.tf"
2. So if variables are declared in variables.tf, it could recognize
3. if we need order, we can name files lexographically.
4. .tfvars or .tfvars.json also be loaded by terraform
5. It can read from enviroment variables as well
6. terraform apply -var-file="custom.tfvars" # to specify explicit variable files

## Terraform state
1. terraform play - always compares changes with state and show the differences
2. terraform destory - destroys what is known as per the state
3. State can be stored locally or remotely on S3
4. terraform state list -- shows list of all resources known to its state
5. terrafrom show
6. will terrafrom init destry existing state? - No, it won't destory state. It might download additional providers


## How does TF and state sync
1. terraform plan - does that automatically
2. terraform state refresh -- but not required due to terraform plan does that

## Hashicorp Language
1. [Hashicorp language blocks](https://github.com/mohanmca/hashicorp/blob/master/terraform/Hands-On%20Labs/Section%2004%20-%20Understand%20Terraform%20Basics/02%20-%20HashiCorp_Configuration_Language.md)
2. Both human and machine readable


## Terraform code

# main.tf
```json
provider "aws" {
    region =   "eu-central-1"
    shared_credential_files = ["/Users/mn/.aws/credentials"]
}

resource "aws_instance" "ec2_example" {
    ami = "ami-03232323929392"
}
resource "resource_type" "resource_name_in_tf_code" {
    ## resource_type according to provder
    ## aws_s3_bucket, aws_s3_bucket_acl
}
```

## Terraform import
```bash
terraform import <RESOURCE_TYPE>.<RESOURCE_NAME> <RESOURCE_ID>
terraform import aws_instance.ec2_example i-1234

RESOURCE_NAME - resource name in code
RESOURCE_ID - resource ID in provider, ex: AWS

```

## AWS Inline ACL
1. Inline AWS S3 ACL is deprecated
2. It should be separate block/resource

## Reffence
1. [Hashicorp](https://github.com/mohanmca/hashicorp)
2. [Providers](https://registry.terraform.io/browse/providers)