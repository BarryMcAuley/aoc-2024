package org.example;

import lombok.RequiredArgsConstructor;
import util.FileUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DayTen {

    public static void main(String[] args) {
        System.out.println("Day 10");
        var aoc = new DayTen();
        aoc.partOne();
    }

    @RequiredArgsConstructor
    static class Node {
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

    static class Edge {
        Node from;
        Node to;
    }

    public void partOne() {
        System.out.println("Part one");

        try {
            var map = FileUtil.readGrid("src/main/resources/dayten.txt", Integer::parseInt, "");
            var trailheads = map.getAll(0);
            System.out.println(trailheads);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}