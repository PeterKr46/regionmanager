package eu.saltyscout.utils;


import eu.saltyscout.regionmanager.region.Region;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Peter on 25.10.2016.
 */
public class PriorityQueue implements Collection<Region> {
    private ArrayList<Region> backend;
    private static final Comparator<Region> comp = (o1, o2) -> o1.getPriority() < o2.getPriority() ? (-1) : (o1.getPriority() > o2.getPriority() ? 1 : 0);

    public PriorityQueue() {
        backend = new ArrayList<>();
    }

    public PriorityQueue(@Nonnull Collection<? extends Region> init) {
        backend = new ArrayList<>(init);
        backend.sort(comp);
    }

    public boolean enqueue(@Nonnull Region value) {
        return add(value);
    }
    
    @Override
    public boolean add(Region region) {
        backend.add(findInsertIndex(region), region);
        return true;
    }
    
    private int findInsertIndex(Region region) {
        if(backend.isEmpty()) {
            return 0;
        }
        int right = backend.size();
        int center = right/2;
        if(comp.compare(region, backend.get(center)) < 0){
            return findInsertIndex(region, 0, center);
        } else {
            return findInsertIndex(region, center+1, right);
        }
    }
    
    private int findInsertIndex(Region region, int left, int right) {
        if(left == right) {
            return left;
        }
        int center = left + ((right-left)/2);
        if(comp.compare(region, backend.get(center)) <= 0){
            return findInsertIndex(region, left, center);
        } else {
            return findInsertIndex(region, center+1, right);
        }
    }

    public Region dequeue() {
        return backend.size() > 0 ? backend.remove(0) : null;
    }

    @Override
    public PriorityQueue clone() {
        return new PriorityQueue(backend);
    }
    
    @Override
    public int size() {
        return backend.size();
    }
    
    @Override
    public boolean isEmpty() {
        return backend.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return backend.contains(o);
    }
    
    @Override
    public Iterator<Region> iterator() {
        return backend.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return backend.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        return backend.toArray(a);
    }
    
    @Override
    public boolean remove(Object o) {
        return backend.remove(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return backend.containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends Region> c) {
        for(Region reg : c) {
            enqueue(reg);
        }
        return true;
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return backend.removeAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        return backend.retainAll(c);
    }
    
    @Override
    public void clear() {
        backend.clear();
    }
}
