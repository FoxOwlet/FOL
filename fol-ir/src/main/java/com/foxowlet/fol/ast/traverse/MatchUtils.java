package com.foxowlet.fol.ast.traverse;


import com.foxowlet.fol.ast.Node;

public final class MatchUtils {
    private MatchUtils() {}

    public static <T> Node match(T left, T right, Node result) {
        return new Matcher().match(left, right).then(result);
    }

    public static Matcher matcher() {
        return new Matcher();
    }

    public static class Matcher {
        private boolean matched;
        private Node result;

        private Matcher() {
            matched = true;
        }

        public <T> Matcher match(T left, T right) {
            matched = left.equals(right);
            return this;
        }

        public Node then(Node result) {
            return matched ? result : null;
        }

        public <T> Matcher cond(T left, T right, Node result) {
            this.result = match(left, right).then(result);
            return this;
        }

        public <T> Node otherwise(T left, T right, Node result) {
            if (this.result != null) {
                return this.result;
            }
            return match(left, right).then(result);
        }

        public Node otherwise(Node result) {
            if (this.result != null) {
                return this.result;
            }
            return result;
        }
    }
}
