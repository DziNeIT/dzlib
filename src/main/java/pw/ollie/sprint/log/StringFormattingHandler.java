/*
 * This file is part of sprintj, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 Oliver Stanley <http://ollie.pw>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pw.ollie.sprint.log;

import pw.ollie.sprint.format.StringFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * A logging {@link Handler} which applies one or more {@link StringFormatter}s'
 * {@link StringFormatter#format(String)} method to the output of the logger.
 */
public class StringFormattingHandler extends Handler {
    /**
     * The {@link List} of {@link StringFormatter}s being applied to the output.
     */
    private List<StringFormatter> formatters;
    /**
     * Whether the {@link StringFormattingHandler} is currently active.
     */
    private boolean active = true;

    public StringFormattingHandler() {
        this.formatters = new ArrayList<>();
    }

    public StringFormattingHandler(Collection<StringFormatter> formatters) {
        this.formatters = new ArrayList<>(formatters);
    }

    public StringFormattingHandler(StringFormatter... formatters) {
        this.formatters = new ArrayList<>(Arrays.asList(formatters));
    }

    @Override
    public void publish(LogRecord record) {
        if (formatters == null || formatters.isEmpty()) {
            return;
        }

        formatters.forEach((formatter) ->
                record.setMessage(formatter.format(record.getMessage())));
    }

    public boolean isActive() {
        return active;
    }

    public StringFormattingHandler setActive(boolean active) {
        this.active = active;
        return this;
    }

    public StringFormattingHandler add(StringFormatter formatter) {
        formatters.add(formatter);
        return this;
    }

    public StringFormattingHandler remove(StringFormatter formatter) {
        formatters.remove(formatter);
        return this;
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
        formatters.clear();
    }
}
