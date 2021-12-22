package main

type MyReader struct{}

// TODO: Add a Read([]byte) (int, error) method to MyReader.

func (my *MyReader) Read(barry []byte) (int, error) {
	size := len(barry)

	for i := 0; i < size; i++ {
		barry[i] = byte('A')
	}
	return size, nil
}

func main() {
	reader.Validate(&MyReader{})
}
