variable "aws_region" {
  description = "AWS Default Region"
  type        = string
}

variable "aws_bucket_name" {
  description = "AWS Bucket Name"
  type        = string
}

variable "force_aws_bucket_destroy" {
  description = "Force AWS bucket destroy"
  type        = bool
}

variable "aws_access_key" {
  description = "AWS Access Key"
  type        = string
  default     = null
  sensitive   = true
}

variable "aws_secret_access_key" {
  description = "AWS Secret Access Key"
  type        = string
  default     = null
  sensitive   = true
}
