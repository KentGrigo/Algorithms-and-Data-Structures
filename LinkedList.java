import java.util.Iterator;

class LinkedList implements Iterable<String> {
    private LinkedList next;
    private String element;

    public LinkedList() {
        this(null);
    }

    public LinkedList(final String element) {
        this(null, element);
    }

    public LinkedList(final LinkedList next, final String element) {
        this.next = next;
        this.element = element;
    }

    public LinkedList add(final String element) {
        if (this.element == null) {
            this.element = element;
            return this;
        } else {
            return new LinkedList(this, element);
        }
    }

    public LinkedList remove() {
        return next;
    }

    public LinkedList remove(final int index) {
        if (index == 0) {
            return remove();
        }

        LinkedList curr = this;
        for (int i = 0; i < index - 1; i++) {
            if (curr.next == null) {
                return this;
            }
            curr = curr.next;
        }
        if (curr.next == null) {
            return this;
        }
        curr.next = curr.next.next;
        return this;
    }

    public LinkedList set(final int index, final String value) {
        LinkedList curr = this;
        for (int i = 0; i < index; i++) {
            if (curr.next == null) {
                return this;
            }
            curr = curr.next;
        }
        curr.element = value;
        return this;
    }

    public boolean hasCycle() {
        LinkedList turtle = this;
        LinkedList hare = this;
        while (hare != null && hare.next != null) {
            hare = hare.next.next;
            turtle = turtle.next;
            if (hare == turtle) {
                return true;
            }
        }
        return false;
    }

    public static void main(final String[] args) {
        main();
    }

    public static void main() {
        final LinkedList elements = new LinkedList().add("first").add("second").add("third");
        System.out.println(elements.toString());
        System.out.println("==============");

        final LinkedList elements1 = new LinkedList().add("first").add("second").add("third").remove(2);
        System.out.println(elements1.toString());
        System.out.println("==============");

        final LinkedList elements2 = new LinkedList().add("first").add("second").add("third").remove(3);
        System.out.println(elements2.toString());
        System.out.println("==============");
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        LinkedList curr = this;
        while (curr != null) {
            if (builder.length() != 0) {
                builder.append(", ");
            }
            builder.append(curr.element);
            curr = curr.next;
        }
        return "(" + builder.toString() + ")";
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            LinkedList curr = LinkedList.this;

            @Override
            public boolean hasNext() {
                return curr.next != null;
            }

            @Override
            public String next() {
                final String element = curr.element;
                curr = curr.next;
                return element;
            }
        };
    }
}
