package main

import (
	"container/heap"
	"errors"
	"fmt"
	"github.com/barrymcauley/aoc-2024/types"
	"github.com/barrymcauley/aoc-2024/utility"
	"log"
	"math"
	"strconv"
	"strings"
)

func main() {
	pqOne, pqTwo, err := loadNumbers()
	if err != nil {
		log.Fatalf("error loading numbers: %s", err.Error())
	}

	calculateDiff(pqOne, pqTwo)
}

func calculateDiff(pqOne *types.PriorityQueue[int], pqTwo *types.PriorityQueue[int]) {
	simTotal := 0
	for pqOne.Len() > 0 && pqTwo.Len() > 0 {
		simTotal += int(math.Abs(float64(heap.Pop(pqOne).(*types.Item[int]).Value - heap.Pop(pqTwo).(*types.Item[int]).Value)))
	}
	fmt.Println(simTotal)
}

func loadNumbers() (*types.PriorityQueue[int], *types.PriorityQueue[int], error) {
	content, err := utility.ReadFile("/Users/barrymcauley/dev/aoc-2024/src/main/resources/dayone.txt")
	if err != nil {
		return nil, nil, errors.New("error reading file")
	}

	pqOne := initPriorityQueue()
	pqTwo := initPriorityQueue()

	for _, line := range content {
		if len(strings.TrimSpace(line)) == 0 {
			continue
		}

		n1, n2, err := parseLine(line)
		if err != nil {
			return nil, nil, fmt.Errorf("error parsing line: %w", err)
		}

		pushNumber(pqOne, n1)
		pushNumber(pqOne, n2)
	}

	return pqOne, pqTwo, nil
}

func parseLine(line string) (int, int, error) {
	nums := strings.Fields(line)
	if len(nums) != 2 {
		return 0, 0, fmt.Errorf("expected 2 numbers, got %d", len(nums))
	}

	n1, err := strconv.Atoi(nums[0])
	if err != nil {
		return 0, 0, fmt.Errorf("parsing first number: %w", err)
	}

	n2, err := strconv.Atoi(nums[1])
	if err != nil {
		return 0, 0, fmt.Errorf("parsing second number: %w", err)
	}

	return n1, n2, nil
}

func pushNumber(pq *types.PriorityQueue[int], num int) {
	heap.Push(pq, &types.Item[int]{
		Priority: num,
		Value:    num,
	})
}

func initPriorityQueue() *types.PriorityQueue[int] {
	pQueue := make(types.PriorityQueue[int], 0)
	heap.Init(&pQueue)
	return &pQueue
}
