/**
 * This package provides reflection utilities which can be incredibly helpful,
 * but rely on the 'sun.reflect' and other sun packages, which is not guaranteed
 * to be present on every JVM. Therefore, this package should be used with
 * caution. If it is not entirely necessary to use this package, you probably
 * shouldn't be using it at all.
 *
 * {@link pw.ollie.sprint.reflect.sun.SilentInstantiator}, for example, uses
 * the {@link sun.reflect.ReflectionFactory} for serialization constructor
 * creation to create an object without calling its constructor, or the
 * {@link sun.misc.Unsafe} object if it is available.
 */
package pw.ollie.sprint.reflect.sun;