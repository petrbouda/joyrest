/*
 * Copyright 2015 Petr Bouda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.stream;

import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility stream class which combines two elements and provides functions upon them.
 * First object is always object from stream and the second object is
 *
 * @param <T> the first input element
 * @param <U> the second input element
 * @author pbouda
 */
public class BiStream<T, U> {

    private final Stream<T> stream;

    private final U object;

    public BiStream(Stream<T> stream, U object) {
        this.stream = stream;
        this.object = object;
    }

    /**
     * Factory method {@link BiStream} class
     *
     * @param stream stream objects
     * @param obj object which is compared to objects from stream
     * @param <T> type of the objects in stream
     * @param <U> type of the compared object
     * @return BiStream object
     */
    public static <T, U> BiStream<T, U> of(Stream<T> stream, U obj) {
        return new BiStream<>(stream, obj);
    }

    /**
     * Compares the objects from stream to the injected object. If the rest of the stream equals null so exception is thrown.
     *
     * @param biPredicate which compare objects from stream and injected object
     * @param e exception which will be thrown if the stream equals {@code null}
     * @return BiStream object
     */
    public BiStream<T, U> throwIfNull(BiPredicate<? super T, ? super U> biPredicate, Supplier<? extends RuntimeException> e) {
        Predicate<T> predicate = (t) -> biPredicate.test(t, object);
        return nonEmptyStream(stream.filter(predicate), e);
    }

    public Optional<T> findAny() {
        return stream.findAny();
    }

    private BiStream<T, U> nonEmptyStream(Stream<T> stream, Supplier<? extends RuntimeException> e) {
        Spliterator<T> spliterator = stream.spliterator();
        Stream<T> localStream = StreamSupport.stream(new BiStreamSpliterator<>(spliterator, e), false);
        return of(localStream, object);
    }

    private static final class BiStreamSpliterator<T> implements Spliterator<T> {

        private final Spliterator<T> it;
        private final Supplier<? extends RuntimeException> e;
        boolean seen;

        private BiStreamSpliterator(Spliterator<T> it, Supplier<? extends RuntimeException> e) {
            this.it = it;
            this.e = e;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            boolean r = it.tryAdvance(action);
            if (!seen && !r) {
                throw e.get();
            }
            seen = true;
            return r;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return it.estimateSize();
        }

        @Override
        public int characteristics() {
            return it.characteristics();
        }
    }
}
