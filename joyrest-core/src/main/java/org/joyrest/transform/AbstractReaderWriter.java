/*
 * Copyright 2015 Petr Bouda
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.joyrest.transform;

import java.nio.charset.Charset;

import org.joyrest.routing.InternalRoute;

/**
 * {@inheritDoc}
 *
 * An adapter pattern implementation of {@link Reader} and {@link Writer} interfaces
 *
 * @author pbouda
 */
public abstract class AbstractReaderWriter implements Reader, Writer {

    protected final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    @Override
    public boolean isReaderCompatible(InternalRoute route) {
        return route.getConsumes().contains(getMediaType());
    }

    @Override
    public boolean isWriterCompatible(InternalRoute route) {
        return route.getProduces().contains(getMediaType());
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractReaderWriter)) {
            return false;
        }

        AbstractReaderWriter that = (AbstractReaderWriter) o;

        return !(getMediaType() != null ?
                     !getMediaType().equals(that.getMediaType()) : that.getMediaType() != null);
    }

    @Override
    public int hashCode() {
        return getMediaType() != null ? getMediaType().hashCode() : 0;
    }

}
