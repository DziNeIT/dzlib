/*
 * This file is part of dzlib, licensed under the MIT License (MIT).
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
package pw.ollie.dzlib.reflect.sun;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import pw.ollie.dzlib.reflect.ReflectException;
import pw.ollie.dzlib.reflect.util.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper methods for reflection which takes place using 'sun.reflect' classes
 * or makes use of {@link sun.misc.Unsafe}
 *
 * @author Ollie [DziNeIT]
 * (static final field code http://www.javaspecialists.eu/archive/Issue161.html)
 */
public final class UnsafeReflection {
    /**
     * The {@link ReflectionFactory} instance. This particular field is used for
     * all uses of {@link ReflectionFactory} in dzlib, so if you want to you
     * can reflect this field to a different value (using the utility method in
     * this class) to make dzlib use your {@link ReflectionFactory}.
     */
    public static final ReflectionFactory FACTORY = ReflectionFactory
            .getReflectionFactory();

    private static Field MODIFIERS_FIELD = null;
    private static Field STRING_VALUE_FIELD = null;

    static {
        MODIFIERS_FIELD = Reflection.getAccessible(Field.class, "modifiers");
        STRING_VALUE_FIELD = Reflection.getAccessible(String.class, "value");

        if (STRING_VALUE_FIELD == null) {
            Field[] all = String.class.getDeclaredFields();
            for (int i = 0; STRING_VALUE_FIELD == null && i < all.length; i++) {
                if (all[i].getType().equals(char[].class)) {
                    STRING_VALUE_FIELD = all[i];
                }
            }
        }
    }

    private static final Map<Field, FieldAccessor> accessors = new HashMap<>();

    /**
     * Sets the value of the given static final {@link Field} to the given
     * value. This code was written by Heinz Kabutz.
     *
     * @param field the {@link Field} to set the value of
     * @param value the new value for the given {@link Field}
     * @throws ReflectException if there is a problem setting the static final
     *         field
     */
    public static void setStaticFinalField(Field field, Object value) {
        FieldAccessor accessor = accessors.get(field);
        if (accessor == null) {
            try {
                field.setAccessible(true);
                int modifiers = MODIFIERS_FIELD.getInt(field);
                modifiers &= ~Modifier.FINAL;
                MODIFIERS_FIELD.setInt(field, modifiers);
                accessor = FACTORY.newFieldAccessor(field, false);
                accessors.put(field, accessor);
            } catch (Exception e) {
                throw new ReflectException("Couldn't set static final field",
                        e);
            }
        }

        try {
            accessor.set(null, value);
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Modifies the actual value of the given {@link String} to the given
     * char[]. This is very unsafe and should not be used in production
     * applications outside of extreme circumstances or circumstances where
     * there are literally no alternatives - probably never because I have no
     * idea why you'd need to do this.
     *
     * Example:
     *
     * <code>
     *     modifyString("mystring", "notmystring".toCharArray());
     *     System.out.println("mystring");
     * </code>
     *
     * The value printed from the code above would be 'notmystring'.
     *
     * @param string the string object to modify the value of
     * @param newVal the new value for the string object
     */
    public static void modifyString(String string, char[] newVal) {
        try {
            STRING_VALUE_FIELD.set(string, newVal);
        } catch (IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    /**
     * Modifies the actual value of the given {@link String} to the char[] value
     * of the given String. If you were to use the following code:
     *
     * <code>
     *     modifyString("mystring", "notmystring");
     *     System.out.println("mystring");
     * </code>
     *
     * The value printed would be 'notmystring'.
     *
     * @param string the string object to modify the value of
     * @param newVal the new value for the string object
     * @see {@link #modifyString(String, char[])}
     */
    public static void modifyString(String string, String newVal) {
        modifyString(string, newVal.toCharArray());
    }

    private UnsafeReflection() {
        throw new UnsupportedOperationException();
    }
}
