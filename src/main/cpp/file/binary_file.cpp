#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

int main() {
    string file_name = "image.dat";
    ofstream file(file_name, ios_base::out | ios_base::binary);
    int rows=2, cols=3;
    vector<float> vec(rows *cols);
    file.write(reinterpret_cast<char*>(&rows), sizeof(rows));
    file.write(reinterpret_cast<char*>(&cols), sizeof(cols));
    file.write(reinterpret_cast<char*>(&vec.front()), vec.size() * sizeof(float));
    return 0;
}