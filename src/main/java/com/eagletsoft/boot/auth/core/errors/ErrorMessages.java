package com.eagletsoft.boot.auth.core.errors;

public interface ErrorMessages {

    String AUTH_TOKEN_NOTEXISTS = "error.auth.token.notexists";
    String AUTH_TOKEN_EXPIRED = "error.auth.token.expired";
    String AUTH_USER_NAMENOTEXISTS = "error.auth.user.namenotexists";
    String AUTH_USER_NOTEXISTS = "error.auth.user.notexists";
    String AUTH_USER_DISABLED = "error.auth.user.disabled";
    String AUTH_USER_CODE_WRONG = "error.auth.user.code.wrong";



    String AUTH_CLIENT_NAMENOTEXISTS = "error.auth.client.namenotexists";
    String AUTH_CLIENT_SECRET_WRONG = "error.auth.client.secret.wrong";
    String AUTH_CLIENT_GRANTTYPE_WRONG = "error.auth.client.granttype.wrong";
    String AUTH_CLIENT_DISABLED = "error.auth.client.disabled";
    String AUTH_CLIENT_RESOURCE_WRONG = "error.auth.client.resource.wrong";
    String AUTH_CLIENT_SCOPE_WRONG = "error.auth.client.scope.wrong";
}
