package util.types;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class Node {
    final int val;
    final int x;
    final int y;
    Set<Edge> inNodes = new HashSet<>();
    Set<Edge> outNodes = new HashSet<>();

    public void addEdge(Node n) {
        Edge e = new Edge();

        if (Math.abs(this.val - n.val) <= 1) {
            if ((this.val < n.val)) {
                e.from = this;
                e.to = n;
                outNodes.add(e);
                n.inNodes.add(e);
            } else {
                e.from = n;
                e.to = this;
                n.outNodes.add(e);
                this.inNodes.add(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "val=" + val +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return val == node.val && x == node.x && y == node.y;
    }
}


