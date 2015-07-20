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
package org.joyrest.examples.combiner;

/**
 * Application configurer properties
 *
 * @author Petr Bouda
 **/
public final class ApplicationProperties {

    /**
     * Used as a default value in task scheduler if the value is not set for the feed during a feed creation. It particularly
     * means, how
     * often will be feed entries of the given refreshed and downloaded.
     **/
    public static final String DEFAULT_REFRESH_PERIOD = "feed.default-refresh-period";

    /**
     * Indicates the number of threads in a pool of the task scheduler which is dedicated to download new entries.
     **/
    public static final String SCHEDULER_POOL_SIZE = "feed.scheduler.pool-size";

}
