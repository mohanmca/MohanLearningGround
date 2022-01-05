package main

import "testing"

func TestAdder(t *testing.T) {
	actual := Add(3, 2)
	expected := 5
	if actual != expected {
		t.Errorf("got %q and want %q", actual, expected)
	}
	actual = Add(3, 5)
	expected = 9
	if actual != expected {
		t.Errorf("got %d and want %d", actual, expected)
	}
}
