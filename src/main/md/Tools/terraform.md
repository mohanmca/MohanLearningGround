## Terraform
1. terraform init - Initializes the working directory containing the Terraform configuration files. It downloads necessary plugins and providers.
2. terraform fmt
3. terraform validae - Validates the syntax and configuration of the Terraform files.
4. terraform plan - dry-run
5. terraform apply
6. terraform show - Shows the current state or plan in a human-readable format.
7. terraform import - Imports existing infrastructure into your Terraform state.
8. terraform destroy - Destroys the Terraform-managed infrastructure. It undoes all the applied changes.

## How to generate existing infra as code
1. To export existing infrastructure into Terraform code, you’ll use a combination of Terraform’s import functionality and manually defining the configuration files (since Terraform doesn't auto-generate configuration files from existing resources). 
2. create placeholder
3. terraform import


## Terraform state
1. terraform play - always compares changes with state and show the differences
2. terraform destory - destroys what is known as per the state
3. State can be stored locally or remotely on S3
4. terraform state list -- shows list of all resources known to its state
5. terrafrom show
