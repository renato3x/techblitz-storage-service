provider "aws" {
  region     = var.aws_region
  secret_key = var.aws_secret_access_key
  access_key = var.aws_access_key
}
