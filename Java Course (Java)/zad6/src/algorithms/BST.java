package algorithms;

class BST<T extends Comparable<T>> implements Dictionary<T> {
    class Node<T extends Comparable<T>> {
        Node<T> left, right;
         T value;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }

        private boolean searchRec(Node<T> current, T x) {
            if (current == null) {
                return false;
            }

            if (x.equals(current.value)) {
                return true;
            }

            return x.compareTo(current.value) < 0
                    ? searchRec(current.left, x)
                    : searchRec(current.right, x);
        }

        private Node<T> insertRec(Node<T> current, T x) {
            if (current == null) {
                return new Node<T>(x);
            }

            if (x.compareTo(current.value) < 0) {
                current.left = insertRec(current.left, x);
            } else if (x.compareTo(current.value) > 0) {
                current.right = insertRec(current.right, x);
            }

            return current;
        }

        private Node<T> removeRec(Node<T> current, T x) {
            if (current == null) {
                return null;
            }

            if (x.equals(current.value)) {
                if (current.left == null && current.right == null) {
                    return null;
                }

                if (current.right == null) {
                    return current.left;
                }

                if (current.left == null) {
                    return current.right;
                }

                T smallest = minRec(current.right);
                current.value = smallest;
                current.right = removeRec(current.right, smallest);

                return current;
            }

            if (x.compareTo(current.value) < 0) {
                current.left = removeRec(current.left, x);
                return current;
            } else{
                current.right = removeRec(current.right, x);
                return current;
            }
        }

        private T minRec(Node<T> current) {
            return current.left == null ? current.value : minRec(current.left);
        }

        private T maxRec(Node<T> current) {
            return current.right == null ? current.value : minRec(current.right);
        }

        private int sizeRec(Node<T> current) {
            if (current == null) {
                return 0;
            } else {
                return (sizeRec(current.left) + 1 + sizeRec(current.right));
            }
        }
    }

    private Node<T> root; // korzeń drzewa BST

    public BST() {
        root = null;
    }

    public BST(T x) {
        root = new Node<T>(x);
    }

    public Node<T> getRoot(){
        return this.root;
    }

    public boolean search(T x) {
        if (root == null) {
            return false;
        } else {
            return root.searchRec(root, x);
        }
    }

    @Override
    public void insert(T x) {
        if (x == null) {
            throw new IllegalArgumentException("Nie można wstawiać elementu pustego do BST!");
        }

        if (root == null) {
            root = new Node<>(x);
        } else {
            root.insertRec(root, x);
        }
    }

    @Override
    public void remove(T x) {
        if (root == null) {
            return;
        }

        root = root.removeRec(root, x);
    }

    @Override
    public T min() {
        if (root == null) {
            throw new IllegalStateException("Puste drzewo nie ma wartości minimalnej!");
        }

        return root.minRec(root);
    }

    @Override
    public T max() {
        if (root == null) {
            throw new IllegalStateException("Puste drzewo nie ma wartości maksymalnej!");
        }

        return root.maxRec(root);
    }

    @Override
    public int size() {
        return root.sizeRec(root);
    }

    @Override
    public void clear() {
        root = null;
    }
}