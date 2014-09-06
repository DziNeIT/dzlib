package pw.ollie.sprint.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Utility for building {@link Collection}s. Many different implementations of
 * {@link Collection} are supported, including most which are present in the
 * Java standard libraries.
 *
 * @param <E> the type for the {@link Collection} being build
 */
public class CollectionBuilder<E> {
    /**
     * The elements build in the {@link CollectionBuilder} so far. These are
     * stored in an {@link ArrayList} by default to make sure that the order is
     * retained in case it is needed by the user.
     */
    private final Collection<E> elements;

    /**
     * Constructs a new {@link CollectionBuilder}, defaulting to the usage of an
     * {@link ArrayList} for elements.
     */
    public CollectionBuilder() {
        this.elements = new ArrayList<>();
    }

    /**
     * Constructs a new {@link CollectionBuilder}, using the given {@link
     * Collection} for the backing store. Note that the {@link Collection} which
     * is passed is the one which is actually used, so any modifications to the
     * {@link Collection} after this constructor is called <em>will</em> affect
     * this builder, and vice versa.
     *
     * @param backing the {@link Collection} to use to store built elements
     */
    public CollectionBuilder(Collection<E> backing) {
        this.elements = backing;
    }

    /**
     * Adds the given object, which <em>MUST</em> be of type E, to the builder.
     * Should the element not be of type E, you <em>WILL</em> get a {@link
     * ClassCastException}.
     *
     * @param element the element to add
     * @return this {@link CollectionBuilder} object
     */
    public CollectionBuilder add(E element) {
        elements.add(element);
        return this;
    }

    /**
     * Adds all of the given objects, which <em>MUST</em> be of type E, to the
     * builder. Should any element given not be of type E, you <em>WILL</em> get
     * {@link ClassCastException}.
     *
     * @param elements the elements to add
     * @return this {@link CollectionBuilder} object
     */
    @SuppressWarnings("unchecked") // if someone is that stupid it's their fault
    public CollectionBuilder add(Object... elements) {
        if (elements == null || elements.length < 1) {
            throw new IllegalArgumentException();
        }
        for (Object object : elements) {
            add((E) object);
        }
        return this;
    }

    /**
     * Sorts the elements of this {@link CollectionBuilder}, ONLY if the backing
     * {@link Collection} being used is an {@link ArrayList}.
     *
     * @return this {@link CollectionBuilder} object
     */
    public CollectionBuilder sort() {
        if (elements instanceof List) {
            Collections.sort((List) elements);
        }
        return this;
    }

    /**
     * Builds the elements added to this builder into a new {@link Collection}
     * of the given {@link CollectionType}.
     *
     * @param type the {@link CollectionType} to use
     * @return a {@link Collection} of elements added to this builder
     */
    public Collection<E> build(CollectionType type) {
        Collection<E> result = type.instantiate();
        result.addAll(elements);
        return result;
    }

    /**
     * Gets a {@link Collection} containing all of the elements added to this
     * builder.
     *
     * @return a {@link Collection} containing all built elements
     */
    public Collection<E> build() {
        return elements;
    }

    /**
     * An alphabetically sorted enum of all significant {@link Collection}
     * implementations within the Java standard libraries.
     */
    // why did i do this?
    public static enum CollectionType {
        ArrayBlockingQueue(ArrayBlockingQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ArrayBlockingQueue<>(16);
            }
        },
        ArrayDeque(ArrayDeque.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ArrayDeque<>();
            }
        },
        ArrayList(ArrayList.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ArrayList<>();
            }
        },
        ConcurrentLinkedDeque(ConcurrentLinkedDeque.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ConcurrentLinkedDeque<>();
            }
        },
        ConcurrentLinkedQueue(ConcurrentLinkedQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ConcurrentLinkedQueue<>();
            }
        },
        ConcurrentSkipListSet(ConcurrentSkipListSet.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new ConcurrentSkipListSet<>();
            }
        },
        CopyOnWriteArrayList(CopyOnWriteArrayList.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new CopyOnWriteArrayList<>();
            }
        },
        CopyOnWriteArraySet(CopyOnWriteArraySet.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new CopyOnWriteArraySet<>();
            }
        },
        HashSet(HashSet.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new HashSet<>();
            }
        },
        LinkedBlockingDeque(LinkedBlockingDeque.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new LinkedBlockingDeque<>();
            }
        },
        LinkedBlockingQueue(LinkedBlockingQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new LinkedBlockingQueue<>();
            }
        },
        LinkedHashSet(LinkedHashSet.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new LinkedHashSet<>();
            }
        },
        LinkedList(LinkedList.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new LinkedList<>();
            }
        },
        LinkedTransferQueue(LinkedTransferQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new LinkedTransferQueue<>();
            }
        },
        PriorityBlockingQueue(PriorityBlockingQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new PriorityBlockingQueue<>();
            }
        },
        PriorityQueue(PriorityQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new PriorityQueue<>();
            }
        },
        SynchronousQueue(SynchronousQueue.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new SynchronousQueue<>();
            }
        },
        Stack(Stack.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new Stack<>();
            }
        },
        TreeSet(TreeSet.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new TreeSet<>();
            }
        },
        Vector(Vector.class) {
            @Override
            public <E> Collection<E> instantiate() {
                return new Vector<>();
            }
        };

        private final Class<? extends Collection> type;

        CollectionType(Class<? extends Collection> type) {
            this.type = type;
        }

        public abstract <E> Collection<E> instantiate();
    }
}
