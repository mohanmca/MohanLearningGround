## How to save time and space in bazel
* Crate .bazelrc
```bazel
# An even simpler example would not configure any extra caching; but
# to speed up the CI used for these examples, it’s worth this extra bit
# of configuration.

# --repository_cache for fetched externals
# --disk_cache for build artifacts

build --repository_cache=~/.cache/bazel-repo
fetch --repository_cache=~/.cache/bazel-repo
query --repository_cache=~/.cache/bazel-repo
build --disk_cache=~/.cache/bazel-disk
```

## Bazel notes
1. Pythonic syntax
2. Make semanics
3. Path based
4. Distributed and remote build is possible in bazel
5. starlak language
6. Built-in rules are stored under
  1. [Java rules - https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/java.bzl](https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/java.bzl)
  1. [http_archive - https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/http.bzl](https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/http.bzl)
7. load("rules_from_remote", "import_name")
  1. load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
  2. https://github.com/bazelbuild/bazel-gazelle
  3. gazelle is build file generator
4. Bazel doesn't deal with version dependency resolution
   5. It delegated to Maven, Ivy, Gradle, etc.


## Bazel commands
1. bazel version
2. bazel info
3. bazel help build

## Reference
1. "@workspace" is a special label that refers to the root of the workspace.
2. @label, @label can be searched within the code space
   1. @label//path/to/package:target_name
3. // -- root of the workspace
4. : -- root of the package
5. / -- root of the directory
6. bazel query "deps(//path/to/package:target_name)"
7. "@maven//:jar" is a special label that refers to the maven_jar rule in the maven repository.
8. "@maven//:junit_jar" is a special label that refers to the junit_jar rule in the maven repository.

## Bazel rules
1. cc_binary
2. java_binary, java_library, java_test, java_toolchain