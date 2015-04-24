package org.joyrest.examples.jokeapp;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class JokeServiceImpl implements JokeService {

	private Map<String, Joke> store = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		Joke joke1 = new Joke("Petr Bouda", "Nice One!!");
		Joke joke2 = new Joke("Sensei", "Almost Better One!!");

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
