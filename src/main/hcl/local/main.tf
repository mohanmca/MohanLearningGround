provider "local" {}

# Variable for the greeting message
variable "greeting" {
  type    = string
  default = "Hello, Terraform!"
}

# Local values to define additional content
locals {
  additional_message = "This is a randomly generated ID:"
  current_dir        = path.module
}

# Generate a random string
resource "random_string" "unique_id" {
  length  = 8
  special = false
}

# Create a file with content including timestamp, random string, and variables
resource "local_file" "example" {
  filename = "${local.current_dir}/example_with_randomness.txt"
  content  = <<EOF
${var.greeting}
Timestamp: ${timestamp()}
${local.additional_message} ${random_string.unique_id.result}
File created in: ${local.current_dir}
EOF
}

# Execute a command to display the file content
resource "null_resource" "execute_command" {
  provisioner "local-exec" {
    command = "cat ${local_file.example.filename}"
  }
}

# Required providers block for the random provider
terraform {
  required_providers {
    random = {
      source  = "hashicorp/random"
      version = "~> 3.0"
    }
  }
}
