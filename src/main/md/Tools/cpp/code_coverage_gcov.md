## Cmake command

1. cmake -G Ninja -DCMAKE_COMPILER=g++ -DCMAKE_C_COMPILER=/usr/bin/gcc -DCMAKE_TOOL_CHAIN_FILE=../KDABViewer/coverage.cmake ../KDABViewer
2. When you run cmake -G Ninja.. it essentially means that you are using a build system namely Ninja.
3. Furthermore, the Ninja in cmake -G Ninja.. will generate Ninja build files.
4. The source directory "/../KDABViewer" should contain CMakeLists.txt.

## Gcov
1.[Introduction GCov](https://github.com/vikasnagpaliitd/linux-prog-tools/blob/master/gcov_notes.pdf)
2. Compiler instruments file for coverage report
3. gcovr --root ../service -j 10 --html-details -o html/ .
4. Alternative to gcov is lcob

   
