## How to save time and space in bazel
* Create .bazelrc
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
1. Pythonic syntax and Make semanics
1. Path based
1. Distributed and remote build is possible in bazel
1. starlark language
1. Built-in rules are stored under
  1. [Java rules - https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/java.bzl](https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/java.bzl)
  1. [http_archive - https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/http.bzl](https://github.com/bazelbuild/bazel/blob/master/tools/build_defs/repo/http.bzl)
1. load("rules_from_remote", "import_name")
  1. load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
  1. https://github.com/bazelbuild/bazel-gazelle
  1. gazelle is build file generator
1. Bazel doesn't deal with version dependency resolution
   1. It is delegated to Maven, Ivy, Gradle, etc.

## Bazel commands
1. bazel version
2. bazel info
3. bazel help build
4. bazel query ...
5. bazel run :frontend //alias based target

## Bazel query
1. How to query all the packages
  1. bazel query //...
    1. It prints the entire tree   
  1. bazel query "deps(//web-module/test/web:tests)"
2. Find reverse dependency
3. bazel query "rdeps(//web-module/test/web:tests)"
4. bazel query "rdeps(..., //apps/node_web:index.js)" --output package

## which packages depend on qtdb lib?
```
bazel query 'rdeps(..., //vistar/geo/qtdb:go_default_library)' --output package 
```

## which packages does qtdb depend on?
```
bazel query 'deps(//vistar/geo/qtdb:go_default_library)' --output package
```

## which rules are defined in package root?
```
bazel query 'kind(rule, //:*)' --output label_kind
```

## get BUILD file output from a build artifact
```
bazel query --noimplicit_deps 'deps(trafficking/ui/selectors.jsar)' --output=build
bazel query --noimplicit_deps 'deps(@docker//:client)' --output=build
```

## Basic bazel concepts
1. WORKSPACE and BUILD
2. WORKSPACE - unique name and only one workspace for one monorepo (google has only one WORKSPACE)
3. Package - Each directory with build file is called package
4. Packages has multiple targets
5. Targets are the smallest units that can be built
6. bazel build //path/to/mypackage:mytarget

## Command Reference
1. "@workspace" is a special label that refers to the root of the workspace.
2. @label, @label can be searched within the code space
   1. @label//path/to/package:target_name
3. // -- root of the workspace
4. : -- root of the package, (check inside the build file)
5. / -- root of the directory
6. bazel query "deps(//path/to/package:target_name)"
7. "@maven//:jar" is a special label that refers to the maven_jar rule in the maven repository.
8. "@maven//:junit_jar" is a special label that refers to the junit_jar rule in the maven repository.

## Bazel rules
1. cc_binary
2. java_binary, java_library, java_test, java_toolchain
3. [We can use alias rule to create a short name for a long target](https://docs.bazel.build/versions/master/be/general.html#alias)

## BES - BuildEventStream
1. Works for multiple build tools, not just for bazel
2. Bazel-BSP - Build Server Protocol is common protocol helps IDE to integrate
3. 

## Reference
1. [Bazel BootCamp](https://www.youtube.com/watch?v=jY0BGMB21hw)
2. [Build Event Protocol for Reclient (Ola Rozenfeld @ EngFlow) - Oct 2023](https://www.youtube.com/watch?v=w6-cMumFDgA)
3. [April 2022: Bazel Custom Rules Workshop (Ulf Adams)](https://www.youtube.com/watch?v=OPmUbpBNK9g&list=PLxx_fSA_YtcV_EcmWXSKVoQcTWba8nO38)
