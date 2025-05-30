## Intellij Bazel
1. [Instructions](https://github.com/bazelbuild/intellij/blob/master/docs/index.md)
2. [What is the Bazel IntelliJ plugin?](https://www.youtube.com/watch?v=GV_KwWK3Qy8)

## Phase model
1. *All models are wrong, but some of them are useful*
2. Loading - Reading & Executing Build Files, List files  glob()
3. Analysis - Each rule can create output files and actions to generate tehm
4. Execution - Actions are executed (possibly remotely)
5. All the above can be executed in pipeline based (like parallel, compare with CPU pipeline), they are executed in interleaved..
6. Gotcha
    1. If you have actions that have output files aren't that depth-set, it may not get executed
    1. Bazel might see no dependent actions that require these outputs. Hence it might miss few

## Bazel Rule
1. A rule defines how Bazel should transform a set of input files into output files, typically by invoking a compiler, linker, or other tools.
2. Rules are used to define new types of targets in your BUILD files. For example, Bazel's built-in rules like cc_library, java_library, etc., are all rules.
3. Rules are more powerful than macros because they define custom logic for how to build a target, and they can access and manipulate files, run commands, etc. However, they are also more complex to write.

## Sample Bazel rule

```python
def _my_rule_impl(ctx):
    # Implementation logic for the rule
    output_file = ctx.actions.declare_file(ctx.label.name + ".out")
    ctx.actions.run(
        outputs = [output_file],
        executable = ctx.executable.my_tool,
        arguments = [ctx.file.src.path, output_file.path],
    )

my_rule = rule(
    implementation = _my_rule_impl,
    attrs = {
        "src": attr.label(allow_single_file = True),
        "my_tool": attr.label(executable = True, cfg = "host"),
    },
    outputs = {"out": "%{name}.out"},
)

# Usage in BUILD file
my_rule(
    name = "my_target",
    src = "foo.txt",
    my_tool = ":my_tool_binary",
)
```

## bazel troubleshoot
1. bazel build target --subcommands --explain=explanation.log
2. bazel query "rdeps(//...,//3rdparty/jvm/com/propensive:*)"
3. bazel query "//3rdparty/jvm/com/propensive:*"

## How to list files in fileGroups
```
bazel query 'kind("source file", deps(//project/sub_project:container_files))'
```

 ## Bazel Macros
 1. A macro is essentially a function written in Starlark (the language used in Bazel's build files). It is used to simplify and reuse common sets of build instructions. Macros can call other rules or macros.
 2. Macros are typically used to reduce repetition in your BUILD files.
 3. Macros allow for more flexibility in structuring your build files because they can take parameters and generate multiple rules or targets.

## Sample bazel macros

```python
def my_macro(name, srcs):
    cc_library(
        name = name,
        srcs = srcs,
        deps = ["//some:dependency"],
    )

# Usage in BUILD file
my_macro(
    name = "mylib",
    srcs = ["foo.cc", "bar.cc"],
)
```

## Bazel commands
1. bazel version
2. bazel info
3. bazel help build
4. bazel query ...
5. bazel run :frontend //alias based target
6. bazel run //target/application -- --jvm_flags="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

## How to do remote debug using bazel
1. bazel run //target/application -- --jvm_flags="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"

## How to create uber jar using bazel
1. Add "_deploy.jar", it would automatically create uber jar
1.
    ```bash
        bazel build module/sub_module:ParquetGenerator
        bazel build module/sub_module:ParquetGenerator_deploy.jar
    ```

## Bazel query
1. How to query all the packages
  1. bazel query //...
    1. It prints the entire tree   
  1. bazel query "deps(//web-module/test/web:tests)"
2. Find reverse dependency
3. bazel query "rdeps(//web-module/test/web:tests)"
4. bazel query "rdeps(..., //apps/node_web:index.js)" --output package
5. Query language can work with cycle - implemented by Alan Donovan 


## Basic bazel concepts
1. WORKSPACE and BUILD
2. WORKSPACE - unique name and only one workspace for one monorepo (google has only one WORKSPACE)
3. Package - Each directory with build file is called package
4. Packages has multiple targets
5. Targets are the smallest units that can be built
6. bazel build //path/to/mypackage:mytarget


## How to find container image tar built by bazel and use it
```
find /private/var/tmp/ -name "*.tar"  | grep "app.sub.directory.name"      | grep image.tar
docker save my_app_image:latest -o my_app_image.tar
docker load -i /private/var/tmp/_bazel_mohan.narayanaswamy/my_app_image.tar
```

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

## Bazel rule
1. It should have name
2. It can have multiple attributes
3. Bazel uses a lots of label instead of string - A *Label is a special type* in Starlark that uniquely identifies a Bazel target.
   1. srcs and targets are generally Label
   2. It creates dependency

## Bazel Rules vs Macros vs Functions
1. Functions - called while evaluating a BUILD file, function can call other functions (but not rules)
2. Macros - Macros can call other functions and rules
3. Rules - Special evaulation model, Each call to a rule creates a node in the BUILD grpah
4. All looks like function calls, all three are written in Starlark

## Starlark
1. Imperative language - with sandbox properties
2. No hardware, or filesystem access (Sandbox mode)
3. Multi-threaded (No shared writable memory, shared read-only data structure is available)
4. No 'Set' type
5. We can't react by looking at the file system easily
6. Completely deterministic (Map iteration across machines are deterministic, No floating-point types)


## [Bazel notes - Starlark Language]
1. [An Overview of the Starlark language](https://laurent.le-brun.eu/blog/an-overview-of-starlark)
1. Pythonic syntax and Make semanics
    1. Python - (class, import, import) + load_function
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


## Bazel rules
1. cc_binary
2. java_binary, java_library, java_test, java_toolchain
3. [We can use alias rule to create a short name for a long target](https://docs.bazel.build/versions/master/be/general.html#alias)

## BES - BuildEventStream
1. Works for multiple build tools, not just for bazel
2. Bazel-BSP - Build Server Protocol is common protocol helps IDE to integrate
3. 

## CQuery in Bazel - (Configurable Query)
1. Cquery is execute during analysis phase, query is executed during loading phase
2. bazel cquery "//my/app:app" --output=build
3. bazel cquery "deps(//my/app:app)" --output=label
4. Bazel queries are faster than cqueries - Because queries does not evaluate configurations.

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

## Example rule
```bzl
binary_alias = rule(
   implementation = _binary_alias_impl,
   excutable = True,
   attrs = {
      "actual": attr.label(allow_single_file=True, executable=True, cfg="exec",),
   },
)

def __binary_alias_imp(ctx):
   out = ctx.actions.declare_file(ctx.label.name + ".exe")
   ctx.actions.symlink(
      output = out,
      target_file = ctx.file.actual,
      is_executable = True,
   )
   return [DefaultInfo(files=depset([out]), executable = out,)]
```
* excutable - bazel run would work
* actual - is of type label (not string), declares dependency, it could be single file

## Reference
1. [Bazel BootCamp](https://www.youtube.com/watch?v=jY0BGMB21hw)
2. [Bazel bootcamp-slides](https://docs.google.com/presentation/d/1vNuuY97NmxP85MLEheYcMDHbpFb6cwSYWPNloBqdrPM/edit?usp=sharing)
2. [Build Event Protocol for Reclient (Ola Rozenfeld @ EngFlow) - Oct 2023](https://www.youtube.com/watch?v=w6-cMumFDgA)
3. [April 2022: Bazel Custom Rules Workshop (Ulf Adams)](https://www.youtube.com/watch?v=OPmUbpBNK9g&list=PLxx_fSA_YtcV_EcmWXSKVoQcTWba8nO38)
4. [An Overview of the Starlark language](https://laurent.le-brun.eu/blog/an-overview-of-starlark)
5. [GothamGo 2017: A Go implementation of the Skylark Configuration Language by Alan Donovan](https://www.youtube.com/watch?v=9P_YKVhncWI)

## How to create anki?
```
mdanki ./src/main/md/Tools/bazel.md bazel.apkg --deck "Mohan::DeepWork::bazel"
```
