package joe.classes.bundle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

/**
 * Declares a method in the class as the string parser (fromString method) for the class. <br>
 * <br>
 * Note: All StringParser methods in parent classes will be ignored, meaning that this interface does not get inherited.
 */
public @interface StringParser {

}
