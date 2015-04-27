package org.joyrest.examples.nondi.jokeapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JokeServiceImpl implements JokeService {

	private Map<String, Joke> store = new ConcurrentHashMap<>();

	public JokeServiceImpl() {
		Joke joke1 = new Joke("Nice One!!", "Petr Bouda");
		Joke joke2 = new Joke("Almost Better One!!", "Sensei");

		store.put(joke1.getId(), joke1);
		store.put(joke2.getId(), joke2);
	}

	@Override
	public Joke save(Joke joke) {
		store.put(joke.getId(), joke);
		return joke;
	}

	@Override
	public Joke get(String id) {
		return store.get(id);
	}

	@Override
	public List<Joke> getAll() {
		return new ArrayList<>(store.values());
	}
}
