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

/**
 * A {@link StringFormatter} which substrings the given {@link String} based on
 * settings.
 */
public class SubstringStringFormatter implements StringFormatter {
    /**
     * The type setting for substringing by index
     */
    public static final int INDEX = 0;
    /**
     * The type setting for substringing by index of a character
     */
    public static final int INDEX_OF = 1;

    private int startType = -1;
    private int endType = -1;
    private int startIndex = -1;
    private int endIndex = -1;
    private char startIndexOf;
    private char endIndexOf;

    public SubstringStringFormatter() {
    }

    public SubstringStringFormatter(int startIndex, int endIndex) {
        this.startType = INDEX;
        this.endType = INDEX;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public SubstringStringFormatter(char startIndexOf, char endIndexOf) {
        this.startType = INDEX_OF;
        this.endType = INDEX_OF;
        this.startIndexOf = startIndexOf;
        this.endIndexOf = endIndexOf;
    }

    public SubstringStringFormatter(int startIndex, char endIndexOf) {
        this.startType = INDEX;
        this.endType = INDEX_OF;
        this.startIndex = startIndex;
        this.endIndexOf = endIndexOf;
    }

    public SubstringStringFormatter(char startIndexOf, int endIndex) {
        this.startType = INDEX_OF;
        this.endType = INDEX;
        this.startIndexOf = startIndexOf;
        this.endIndex = endIndex;
    }

    @Override
    public String format(String intake) {
        int start = 0;
        int end = intake.length();

        if (startType == INDEX) {
            start = startIndex;
            if (start > intake.length()) {
                start = intake.length();
            }
        } else if (startType == INDEX_OF) {
            start = intake.indexOf(startIndexOf);
            if (start == -1) {
                start = 0;
            }
        }

        if (endType == INDEX) {
            end = endIndex;
            if (end > intake.length()) {
                end = intake.length();
            }
        } else if (endType == INDEX_OF) {
            end = intake.lastIndexOf(endIndexOf);
            if (end == -1) {
                end = intake.length();
            }
        }

        return intake.substring(start, end);
    }

    public int getStartType() {
        return startType;
    }

    public int getEndType() {
        return endType;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public char getStartIndexOf() {
        return startIndexOf;
    }

    public char getEndIndexOf() {
        return endIndexOf;
    }

    public SubstringStringFormatter startType(int startType) {
        if (startType != INDEX && startType != INDEX_OF) {
            throw new IllegalArgumentException();
        }
        this.startType = startType;
        return this;
    }

    public SubstringStringFormatter endType(int endType) {
        if (endType != INDEX && endType != INDEX_OF) {
            throw new IllegalArgumentException();
        }
        this.endType = endType;
        return this;
    }

    public SubstringStringFormatter startIndex(int startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public SubstringStringFormatter endIndex(int endIndex) {
        this.endIndex = endIndex;
        return this;
    }

    public SubstringStringFormatter startIndexOf(char startIndexOf) {
        this.startIndexOf = startIndexOf;
        return this;
    }

    public SubstringStringFormatter endIndexOf(char endIndexOf) {
        this.endIndexOf = endIndexOf;
        return this;
    }
}
