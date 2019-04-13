/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Oliver Stanley <http://ollie.pw>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pw.ollie.dzlib.collect;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
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
 * All significant {@link Collection} implementations within the Java standard
 * libraries and some others, sorted alphabetically.
 */
public abstract class CollectionType<T extends Collection> {
    private static final Map<Class<? extends Collection>, CollectionType> types = new HashMap<>();

    public static final CollectionType<ArrayBlockingQueue> ArrayBlockingQueue = new CollectionType<ArrayBlockingQueue>(
            ArrayBlockingQueue.class) {
        @Override
        public ArrayBlockingQueue instantiate() {
            return new ArrayBlockingQueue<>(16);
        }
    };
    public static final CollectionType<ArrayDeque> ArrayDeque = new CollectionType<ArrayDeque>(
            ArrayDeque.class) {
        @Override
        public ArrayDeque instantiate() {
            return new ArrayDeque<>();
        }
    };
    public static final CollectionType<ArrayList> ArrayList = new CollectionType<ArrayList>(
            ArrayList.class) {
        @Override
        public ArrayList instantiate() {
            return new ArrayList<>();
        }
    };
    public static final CollectionType<ConcurrentLinkedDeque> ConcurrentLinkedDeque = new CollectionType<ConcurrentLinkedDeque>(
            ConcurrentLinkedDeque.class) {
        @Override
        public ConcurrentLinkedDeque instantiate() {
            return new ConcurrentLinkedDeque<>();
        }
    };
    public static final CollectionType<ConcurrentLinkedQueue> ConcurrentLinkedQueue = new CollectionType<ConcurrentLinkedQueue>(
            ConcurrentLinkedQueue.class) {
        @Override
        public ConcurrentLinkedQueue instantiate() {
            return new ConcurrentLinkedQueue<>();
        }
    };
    public static final CollectionType<ConcurrentSkipListSet> ConcurrentSkipListSet = new CollectionType<ConcurrentSkipListSet>(
            ConcurrentSkipListSet.class) {
        @Override
        public ConcurrentSkipListSet instantiate() {
            return new ConcurrentSkipListSet<>();
        }
    };
    public static final CollectionType<CopyOnWriteArrayList> CopyOnWriteArrayList = new CollectionType<CopyOnWriteArrayList>(
            CopyOnWriteArrayList.class) {
        @Override
        public CopyOnWriteArrayList instantiate() {
            return new CopyOnWriteArrayList<>();
        }
    };
    public static final CollectionType<CopyOnWriteArraySet> CopyOnWriteArraySet = new CollectionType<CopyOnWriteArraySet>(
            CopyOnWriteArraySet.class) {
        @Override
        public CopyOnWriteArraySet instantiate() {
            return new CopyOnWriteArraySet<>();
        }
    };
    public static final CollectionType<HashSet> HashSet = new CollectionType<HashSet>(
            HashSet.class) {
        @Override
        public HashSet instantiate() {
            return new HashSet<>();
        }
    };
    public static final CollectionType<LinkedBlockingDeque> LinkedBlockingDeque = new CollectionType<LinkedBlockingDeque>(
            LinkedBlockingDeque.class) {
        @Override
        public LinkedBlockingDeque instantiate() {
            return new LinkedBlockingDeque<>();
        }
    };
    public static final CollectionType<LinkedBlockingQueue> LinkedBlockingQueue = new CollectionType<LinkedBlockingQueue>(
            LinkedBlockingQueue.class) {
        @Override
        public LinkedBlockingQueue instantiate() {
            return new LinkedBlockingQueue<>();
        }
    };
    public static final CollectionType<LinkedHashSet> LinkedHashSet = new CollectionType<LinkedHashSet>(
            LinkedHashSet.class) {
        @Override
        public LinkedHashSet instantiate() {
            return new LinkedHashSet<>();
        }
    };
    public static final CollectionType<LinkedList> LinkedList = new CollectionType<LinkedList>(
            LinkedList.class) {
        @Override
        public LinkedList instantiate() {
            return new LinkedList<>();
        }
    };
    public static final CollectionType<LinkedTransferQueue> LinkedTransferQueue = new CollectionType<LinkedTransferQueue>(
            LinkedTransferQueue.class) {
        @Override
        public LinkedTransferQueue instantiate() {
            return new LinkedTransferQueue<>();
        }
    };
    public static final CollectionType<PagedArrayList> SimplePagedList = new CollectionType<PagedArrayList>(
            PagedArrayList.class) {
        @Override
        public PagedArrayList instantiate() {
            return new PagedArrayList<>();
        }
    };
    public static final CollectionType<PriorityBlockingQueue> PriorityBlockingQueue = new CollectionType<PriorityBlockingQueue>(
            PriorityBlockingQueue.class) {
        @Override
        public PriorityBlockingQueue instantiate() {
            return new PriorityBlockingQueue<>();
        }
    };
    public static final CollectionType<PriorityQueue> PriorityQueue = new CollectionType<PriorityQueue>(
            PriorityQueue.class) {
        @Override
        public PriorityQueue instantiate() {
            return new PriorityQueue<>();
        }
    };
    public static final CollectionType<SynchronousQueue> SynchronousQueue = new CollectionType<SynchronousQueue>(
            SynchronousQueue.class) {
        @Override
        public SynchronousQueue instantiate() {
            return new SynchronousQueue<>();
        }
    };
    public static final CollectionType<Stack> Stack = new CollectionType<Stack>(
            Stack.class) {
        @Override
        public Stack instantiate() {
            return new Stack<>();
        }
    };
    public static final CollectionType<TreeSet> TreeSet = new CollectionType<TreeSet>(
            TreeSet.class) {
        @Override
        public TreeSet instantiate() {
            return new TreeSet<>();
        }
    };
    public static final CollectionType<Vector> Vector = new CollectionType<Vector>(
            Vector.class) {
        @Override
        public Vector instantiate() {
            return new Vector<>();
        }
    };

    public CollectionType(Class<T> type) {
        types.put(type, this);
    }

    /**
     * Instantiate an instance of the {@link Collection} implementation which
     * this {@link CollectionType} represents.
     *
     * @return a new {@link Collection} of this {@link CollectionType}
     */
    public abstract T instantiate();

    @SuppressWarnings("unchecked")
    public static <T extends Collection> CollectionType<T> get(
            Class<T> collectionClass) {
        return types.get(collectionClass);
    }
}
