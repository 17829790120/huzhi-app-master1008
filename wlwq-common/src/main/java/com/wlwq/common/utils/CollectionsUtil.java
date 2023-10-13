package com.wlwq.common.utils;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Description:集合转换处理工具类
 * @Author: ck
 * @Date: 2022/11/16 15:06
 */

public class CollectionsUtil {
    public CollectionsUtil() {
    }

    public static <E> void forEach(Collection<E> collection, Consumer<? super E> action) {
        if(!isEmpty(collection) && action != null) {
            collection.forEach(action);
        }
    }

    public static <F, T> List<T> map(Collection<F> source, Function<F, T> func) {
        if(isEmpty(source)) {
            return Collections.emptyList();
        } else {
            List<T> resultList = new ArrayList();
            Iterator var3 = source.iterator();

            while(var3.hasNext()) {
                F f = (F) var3.next();
                T t = func.apply(f);
                resultList.add(t);
            }

            return resultList;
        }
    }

    public static <F, T> List<T> mapIndex(Collection<F> source, BiFunction<F, Integer, T> func) {
        if(isEmpty(source)) {
            return Collections.emptyList();
        } else {
            List<T> resultList = new ArrayList();
            AtomicInteger index = new AtomicInteger();
            Iterator var4 = source.iterator();

            while(var4.hasNext()) {
                F f = (F) var4.next();
                resultList.add(func.apply(f, index.getAndIncrement()));
            }

            return resultList;
        }
    }

    public static <F> String mapJoin(Collection<F> source, Function<F, String> func, String sep) {
        return isEmpty(source) ? "" : (String) source.stream().map(func).distinct().collect(Collectors.joining(sep));
    }

    public static <E> List<E> filter(Collection<E> source, Predicate<E> predicate) {
        if(!isEmpty(source) && predicate != null) {
            List<E> result = new ArrayList();
            Iterator var3 = source.iterator();

            while(var3.hasNext()) {
                E element = (E) var3.next();
                if(predicate.test(element)) {
                    result.add(element);
                }
            }

            return result;
        } else {
            return Collections.emptyList();
        }
    }

    public static <F, T> List<T> filterNotNull(Collection<F> source, Function<F, T> func) {
        return filter(map(source, func), Objects::nonNull);
    }

    public static <T> boolean isEmpty(Collection<T> source) {
        return source == null || source.size() <= 0;
    }

    public static <E> E findFirst(Collection<E> source) {
        return findFirst(source, (e)->{
            return true;
        });
    }

    public static <E> E findFirst(Collection<E> elements, Predicate<E> predicate) {
        if(isEmpty(elements)) {
            return null;
        } else {
            Iterator var2 = elements.iterator();

            Object element;
            do {
                if(!var2.hasNext()) {
                    return null;
                }

                element = var2.next();
            } while(!predicate.test((E) element));

            return (E) element;
        }
    }

    public static <F1, F2, K, R> List<R> leftJoin(Collection<F1> left, Collection<F2> right, Function<F1, K> leftKeyBuilder, Function<F2, K> rightKeyBuilder, BiFunction<F1, F2, R> resultBuilder) {
        if(isEmpty(left)) {
            return Collections.emptyList();
        } else {
            Map<K, F2> rightMap = toMap(right, rightKeyBuilder);
            List<R> result = new ArrayList();
            Iterator var7 = left.iterator();

            while(var7.hasNext()) {
                F1 element = (F1) var7.next();
                F2 rightElement = rightMap.get(leftKeyBuilder.apply(element));
                result.add(resultBuilder.apply(element, rightElement));
            }

            return result;
        }
    }

    public static <K, E> Map<K, E> toMap(Collection<E> elements, Function<E, K> keyBuilder) {
        if(!isEmpty(elements) && keyBuilder != null) {
            Map<K, E> result = Maps.newHashMap();
            Iterator var3 = elements.iterator();

            while(var3.hasNext()) {
                E element = (E) var3.next();
                result.put(keyBuilder.apply(element), element);
            }

            return result;
        } else {
            return Maps.newHashMap();
        }
    }

    public static <K, E, V> Map<K, V> toMap(Collection<E> elements, Function<E, K> keyBuilder, Function<E, V> valueBuilder) {
        if(!isEmpty(elements) && keyBuilder != null) {
            Map<K, V> result = Maps.newHashMap();
            Iterator var4 = elements.iterator();

            while(var4.hasNext()) {
                E element = (E) var4.next();
                result.put(keyBuilder.apply(element), valueBuilder.apply(element));
            }

            return result;
        } else {
            return Maps.newHashMap();
        }
    }

    public static <K, E> Map<K, E> flatMap(Collection<E> elements, Function<E, List<K>> spiltFunc) {
        if(!isEmpty(elements) && spiltFunc != null) {
            Map<K, E> result = Maps.newHashMap();
            Iterator var3 = elements.iterator();

            while(true) {
                Object element;
                List keys;
                do {
                    if(!var3.hasNext()) {
                        return result;
                    }

                    element = var3.next();
                    keys = (List) spiltFunc.apply((E) element);
                } while(isEmpty(keys));

                Iterator var6 = keys.iterator();

                while(var6.hasNext()) {
                    K key = (K) var6.next();
                    result.put(key, (E) element);
                }
            }
        } else {
            return Maps.newHashMap();
        }
    }

    public static <E, K> Map<K, List<E>> toMapList(Collection<E> elements, Function<E, K> keyFunc) {
        return toMapList(elements, keyFunc, Function.identity());
    }

    public static <E, K, V> Map<K, List<V>> toMapList(Collection<E> elements, Function<E, K> keyFunc, Function<E, V> valueFunc) {
        if(!isEmpty(elements) && keyFunc != null && valueFunc != null) {
            Map<K, List<V>> result = Maps.newHashMap();
            Iterator var4 = elements.iterator();

            while(var4.hasNext()) {
                E element = (E) var4.next();
                K key = keyFunc.apply(element);
                List<V> values = (List) result.computeIfAbsent(key, (k)->{
                    return new ArrayList();
                });
                values.add(valueFunc.apply(element));
            }

            return result;
        } else {
            return Collections.emptyMap();
        }
    }

    public static <E, K, R> Map<K, R> groupBy(Collection<E> elements, Function<E, K> keyFunc, BiFunction<K, List<E>, R> resultBuilder) {
        if(!isEmpty(elements) && keyFunc != null && resultBuilder != null) {
            Map<K, List<E>> map = toMapList(elements, keyFunc, Function.identity());
            Map<K, R> resultMap = Maps.newHashMap();
            Iterator var5 = map.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<K, List<E>> entry = (Map.Entry) var5.next();
                resultMap.put(entry.getKey(), resultBuilder.apply(entry.getKey(), entry.getValue()));
            }

            return resultMap;
        } else {
            return Collections.emptyMap();
        }
    }

    public static <E, R> R reduce(Collection<E> elements, R initValue, BiFunction<R, E, R> reduceFunc) {
        if(isEmpty(elements)) {
            return initValue;
        } else {
            R result = initValue;

            Object element;
            for(Iterator var4 = elements.iterator(); var4.hasNext(); result = reduceFunc.apply(result, (E) element)) {
                element = var4.next();
            }

            return result;
        }
    }

    public static <E> List<E> combine(Collection<Collection<E>> es) {
        if(isEmpty(es)) {
            return Collections.emptyList();
        } else {
            List<E> result = new ArrayList();
            Iterator var2 = es.iterator();

            while(var2.hasNext()) {
                Collection<E> e = (Collection) var2.next();
                if(!isEmpty(e)) {
                    result.addAll(e);
                }
            }

            return result;
        }
    }
}
