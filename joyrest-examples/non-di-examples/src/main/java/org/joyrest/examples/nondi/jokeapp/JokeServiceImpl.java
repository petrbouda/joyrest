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
