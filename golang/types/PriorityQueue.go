package types

type Item[T any] struct {
	Value    T
	Priority int
	index    int
}

type PriorityQueue[T any] []*Item[T]

func (p PriorityQueue[T]) Len() int {
	return len(p)
}

func (p PriorityQueue[T]) Less(i, j int) bool {
	return p[i].Priority > p[j].Priority
}

func (p PriorityQueue[T]) Swap(i, j int) {
	p[i], p[j] = p[j], p[i]
	p[i].index = j
	p[j].index = i
}

func (p *PriorityQueue[T]) Push(x any) {
	n := len(*p)
	item := x.(*Item[T])
	item.index = n
	*p = append(*p, item)
}

func (p *PriorityQueue[T]) Pop() any {
	old := *p
	n := len(old)
	item := old[n-1]
	old[n-1] = nil
	item.index = -1
	*p = old[0 : n-1]
	return item
}
