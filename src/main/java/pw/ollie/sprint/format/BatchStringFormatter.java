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
package pw.ollie.sprint.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Used to apply a batch of {@link StringFormatter}s to a {@link String}.
 */
public class BatchStringFormatter implements StringFormatter {
    /**
     * The {@link List} of {@link StringFormatter}s.
     */
    private List<StringFormatter> formatters;

    public BatchStringFormatter() {
        this.formatters = new ArrayList<>();
    }

    public BatchStringFormatter(Collection<StringFormatter> formatters) {
        this.formatters = new ArrayList<>(formatters);
    }

    public BatchStringFormatter(StringFormatter... formatters) {
        this.formatters = new ArrayList<>(Arrays.asList(formatters));
    }

    @Override
    public String format(String intake) {
        if (formatters == null || formatters.isEmpty()) {
            return intake;
        }

        for (StringFormatter formatter : formatters) {
            intake = formatter.format(intake);
        }
        return intake;
    }

    public BatchStringFormatter add(StringFormatter formatter) {
        if (formatter == this) {
            throw new IllegalArgumentException();
        }
        formatters.add(formatter);
        return this;
    }

    public BatchStringFormatter remove(StringFormatter formatter) {
        formatters.remove(formatter);
        return this;
    }
}
