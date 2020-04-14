package controllers;

import handlers.register.IRegisterHandler;

public class RegisterController {
    private static IRegisterHandler handler;

    public static void setHandler(IRegisterHandler handler) {
        RegisterController.handler = handler;
    }
}
