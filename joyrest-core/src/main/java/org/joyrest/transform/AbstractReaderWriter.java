package org.joyrest.transform;

import org.joyrest.routing.EntityRoute;

public abstract class AbstractReaderWriter implements Reader, Writer {

	@Override
	public boolean isReaderCompatible(EntityRoute route) {
		return route.getConsumes().contains(getMediaType());
	}

	@Override
	public boolean isWriterCompatible(EntityRoute route) {
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
		if (!(o instanceof JsonReaderWriter))
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
