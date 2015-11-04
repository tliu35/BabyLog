package com.example.tong.babylog;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Tong on 10/31/15.
 */
public class BabyData {


    List<Baby> babys = new List<Baby>() {
        @Override
        public void add(int location, Baby object) {
            babys.add(object);
        }

        @Override
        public boolean add(Baby object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends Baby> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Baby> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Baby get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Baby> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<Baby> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Baby> listIterator(int location) {
            return null;
        }

        @Override
        public Baby remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Baby set(int location, Baby object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<Baby> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };

    Calendar date;
    Date day = date.getTime();

    Baby baby_default = new Baby("Baby","female",day );
    //babys.add(0, baby_default);

}
