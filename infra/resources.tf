resource "aws_s3_bucket" "techblitz_main_bucket" {
  bucket        = var.aws_bucket_name
  force_destroy = var.force_aws_bucket_destroy
}
