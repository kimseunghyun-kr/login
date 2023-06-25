package hello.login.web.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}

//@Target(ElementType.PARAMETER) : to be only used on parameters
//@Retention(RetentionPolicy.RUNTIME) : the annotation remains even during runtime(after javac parse), enabling features such as reflection
//where JAVA reflection ==
//    allows an executing Java program to examine or "introspect" upon itself,
//    and manipulate internal properties of the program.
//    For example, it's possible for a Java class to obtain the names of all its members and display them.
//    further docs:
//    https://www.oracle.com/technical-resources/articles/java/javareflection.html#:~:text=Reflection%20is%20a%20feature%20in,its%20members%20and%20display%20them.
