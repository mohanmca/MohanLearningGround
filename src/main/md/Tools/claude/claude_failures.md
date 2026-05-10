## Where it fails
1. If you aske to generate code sometime it used old terraform module instead of latest one 0.30.0 instead of 0.38.0 (git@github.com:gemini/terraform-aws-aurora-global.git//module?ref=0.30.0)
2. You ask to rename a variable, it changes in N file and missing in K files
3. Spoofing testcases and giving false hopes, don't trust it.
4. Sometimes it assumes it is already in recent code, not asking us to check if we need it on latest main
5. It creates too many bazel target, sometime we need consolidated target, agent creating too many fine grained target.
