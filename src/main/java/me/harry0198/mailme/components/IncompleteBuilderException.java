package me.harry0198.mailme.components;

public class IncompleteBuilderException extends Throwable {

    public IncompleteBuilderException() {
        super("Builder incomplete! You must assign all required values.");
    }
}
