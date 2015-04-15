package org.joyrest.examples.combiner.generator;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A simple ID generator based on the sequence generating.
 * 
 * New IDs are generated in order and every returned ID is unique for the instance of generater in which was created.
 * 
 * */
public final class SequenceIdGenerator implements IdGenerator {

	/* Sequence holder */
	private final AtomicLong sequence = new AtomicLong(1);

	/**
	 * Generated unique ID in sequence for the given instance
	 * 
	 * @return generated ID
	 * */
	public String getId() {
		return String.valueOf(sequence.getAndIncrement());
	}

}
