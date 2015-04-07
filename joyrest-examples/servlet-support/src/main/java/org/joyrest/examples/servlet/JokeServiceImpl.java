package org.joyrest.examples.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JokeServiceImpl implements JokeService {

	private Map<String, Joke> datastore = new ConcurrentHashMap<>();

	@Override
	public Joke save(Joke joke) {
		datastore.put(joke.getId(), joke);
		return joke;
	}

	@Override
	public Joke get(String id) {
		return datastore.get(id);
	}

	@Override
	public List<Joke> getAll() {
		return new ArrayList<>(datastore.values());
	}
}
