package cn.edu.tit.training.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class ProcessMessage {

    public static Map<String,Object> currentHashMap = new ConcurrentMap<String, Object>() {
        @Override
        public Object putIfAbsent(String key, Object value) {
            return null;
        }

        @Override
        public boolean remove(Object key, Object value) {
            return false;
        }

        @Override
        public boolean replace(String key, Object oldValue, Object newValue) {
            return false;
        }

        @Override
        public Object replace(String key, Object value) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Object get(Object key) {
            return null;
        }

        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Override
        public Object remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends String, ?> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<String> keySet() {
            return null;
        }

        @Override
        public Collection<Object> values() {
            return null;
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return null;
        }
    };
}
