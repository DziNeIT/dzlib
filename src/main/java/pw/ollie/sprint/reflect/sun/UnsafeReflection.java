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
package pw.ollie.sprint.reflect.sun;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

import pw.ollie.sprint.reflect.ReflectException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Helper methods for reflection which takes place using 'sun.reflect' classes
 * or makes use of {@link sun.misc.Unsafe}
 *
 * @author Ollie [DziNeIT]
 * (static final field code http://www.javaspecialists.eu/archive/Issue161.html)
 */
public class UnsafeReflection {
    /**
     * The {@link ReflectionFactory} instance. This particular field is used for
     * all uses of {@link ReflectionFactory} in sprint, so if you want to you
     * can reflect this field to a different value (using the utility method in
     * this class) to make sprint use your {@link ReflectionFactory}.
     */
    public static final ReflectionFactory FACTORY = ReflectionFactory
            .getReflectionFactory();

    private static final String MODIFIERS_FIELD = "modifiers";
    private static final Field stringValField;

    static {
        Field temp = null;
        try {
            temp = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException ex) {
            Field[] all = String.class.getDeclaredFields();
            for (int i = 0; temp == null && i < all.length; i++) {
                if (all[i].getType().equals(char[].class)) {
                    temp = all[i];
                }
            }
        }

        if (temp != null) {
            temp.setAccessible(true);
            stringValField = temp;
        } else {
            stringValField = null;
        }
    }

    private static boolean avoidUnsafe = true;

    public static boolean isAvoidUnsafe() {
        return avoidUnsafe;
    }

    public static void avoidUnsafe() {
        avoidUnsafe = true;
    }

    public static void useUnsafe() {
        avoidUnsafe = false;
    }

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
        try {
            field.setAccessible(true);
            Field modifiersField = Field.class
                    .getDeclaredField(MODIFIERS_FIELD);
            modifiersField.setAccessible(true);
            int modifiers = modifiersField.getInt(field);
            modifiers &= ~Modifier.FINAL;
            modifiersField.setInt(field, modifiers);
            FieldAccessor fa = FACTORY.newFieldAccessor(field, false);
            fa.set(null, value);
        } catch (Exception e) {
            throw new ReflectException("Couldn't set static final field", e);
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
            stringValField.set(string, newVal);
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
