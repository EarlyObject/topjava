package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        System.out.format("Hello TopJava Enterprise!");
        System.out.println();
        System.out.println(System.getenv("TOPJAVA_ROOT"));


        log.debug("TOPJAVA_ROOT: " + System.getenv("TOPJAVA_ROOT"));
    }
}
