package utility

import (
	"fmt"
	"os"
	"strings"
)

func ReadFile(path string) ([]string, error) {
	content, err := os.ReadFile(path)
	if err != nil {
		return []string{}, fmt.Errorf("error reading file at path: %s", path)
	}

	lines := strings.Split(string(content), "\n")
	return lines, nil
}
