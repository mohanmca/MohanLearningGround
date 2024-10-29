## List of libraries being used
1. [https://github.com/hyperrealm/libconfig](https://hyperrealm.github.io/libconfig/libconfig_manual.pdf)
   1. C/C++ library for processing configuration files
2. [A modern formatting library](https://github.com/fmtlib/fmt)
3. [Fast C++ logging library](https://github.com/gabime/spdlog)
4. [JSON for modern C++](https://github.com/nlohmann/json)
5. [Modern open source C++ FIX framework](https://github.com/fix8/fix8)
6. [GRPC C++](https://github.com/grpc/grpc/tree/master/src/cpp)
7. [SBE C++](https://github.com/real-logic/simple-binary-encoding/wiki/Cpp-User-Guide)
8. [K8S-EKS]
9. [Envoy, Istio, Haproxy, Loadbalancer - learn well]

## Why hyperrealm libconfig being used?
1. Libconfig is a library for reading, manipulating, and writing structured configuration files.
2. More readable and compact than XML and more flexible than the obsolete but prevalent Windows “INI” file format.

## [hyperrealm libconfig features](https://hyperrealm.github.io/libconfig/libconfig_manual.html#Introduction)
1.  name = value; or name: value ;
2.  groups - { settings ... } // but each seettings must have a unique name within the group
3.  Arrays - [ value, value ... ]
4.  Lists - ( value, value ... )
   1.  A list may have zero or more elements, each of which can be a scalar value, an array, a group, or another list.
   1.  The last element in a list may be followed by a comma, which will be ignored.
5. 64-bit Integer Values - For example, ‘0L’ indicates a 64-bit integer value 0.
6. Boolean Values : 'true' or 'false'
7. Include Directives - @include "quote.cfg"
      ```
         # file: test.cfg
         info: {
           name = "Winston Churchill";
           @include "quote.cfg"
           country = "UK";
         };
      ```

## CPP API
1. #include <libconfig.h++>
1. using namespace libconfig;



## Generate MdAnki

```bash
mdanki to_be_more_productive_at_work.md to_be_more_productive_at_work.apkg --deck "Mohan::DeepWork::cpp::MoreProductive"
```
