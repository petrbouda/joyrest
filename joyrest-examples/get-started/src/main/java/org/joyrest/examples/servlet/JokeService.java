package org.joyrest.examples.servlet;

import java.util.List;

public interface JokeService {

	Joke save(Joke joke);

	Joke get(String id);

	List<Joke> getAll();

}
