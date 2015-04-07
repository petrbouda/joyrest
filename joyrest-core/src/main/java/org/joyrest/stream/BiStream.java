package org.joyrest.stream;

import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BiStream<T, U> {

    private final Stream<T> stream;

    private final U object;

    public BiStream(Stream<T> stream, U object) {
        this.stream = stream;
        this.object = object;
    }

    public static <T, U> BiStream<T, U> of(Stream<T> stream, U validatedObject) {
        return new BiStream<>(stream, validatedObject);
    }

    public BiStream<T, U> throwFilter(Predicate<? super T> predicate, Supplier<? extends RuntimeException> e) {
        return nonEmptyStream(stream.filter(predicate), e);
    }

    public BiStream<T, U> throwFilter(BiPredicate<? super T, U> biPredicate, Supplier<? extends RuntimeException> e) {
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
            if (!seen && !r) throw e.get();
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
