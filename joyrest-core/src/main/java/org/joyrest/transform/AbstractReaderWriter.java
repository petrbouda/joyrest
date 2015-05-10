package org.joyrest.transform;

import org.joyrest.routing.InternalRoute;

public abstract class AbstractReaderWriter implements Reader, Writer {

	protected final String DEFAULT_CHARSET = "UTF-8";

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
		if (this == o)
			return true;
		if (!(o instanceof AbstractReaderWriter))
			return false;

		AbstractReaderWriter that = (AbstractReaderWriter) o;

		return !(getMediaType() != null ?
				!getMediaType().equals(that.getMediaType()) : that.getMediaType() != null);
	}

	@Override
	public int hashCode() {
		return getMediaType() != null ? getMediaType().hashCode() : 0;
	}

}
