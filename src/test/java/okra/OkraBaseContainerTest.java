/*
 * Copyright (c) 2017 Okra Scheduler
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package okra;

import com.mongodb.MongoClient;
import okra.base.Okra;
import okra.builder.OkraSimpleBuilder;
import okra.model.DefaultOkraItem;
import org.junit.Before;
import org.junit.ClassRule;
import org.testcontainers.containers.GenericContainer;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public abstract class OkraBaseContainerTest {

    @ClassRule
    public static GenericContainer mongoContainer = new GenericContainer("mongo:3.4").withExposedPorts(27017);

    private Okra<DefaultOkraItem> okraSimple;

    @Before
    public void setUp() throws UnknownHostException {
        okraSimple = new OkraSimpleBuilder<DefaultOkraItem>()
                .withMongoTemplate(getDefaultMongo())
                .withDatabase("okraSimpleTests")
                .withCollection("okraSimple")
                .withExpiration(5, TimeUnit.MINUTES)
                .withItemClass(DefaultOkraItem.class)
                .build();
    }

    public MongoClient getDefaultMongo() {
        return new MongoClient(
                mongoContainer.getContainerIpAddress(),
                mongoContainer.getMappedPort(27017)
        );
    }

    public Okra<DefaultOkraItem> getDefaultOkra() {
        return okraSimple;
    }
}