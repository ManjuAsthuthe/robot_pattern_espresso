package com.fredrikbogg.android_chat_app.test_data.models

data class TestData(
    val validLoginData: ValidLoginData,
    val inValidLoginData: InValidLoginData,
    val changeStatus: ChangeStatus,
    val chat: Chat,
    val user:User,
    val snackBarError: SnackBarError
)

data class ValidLoginData(
    val email: String,
    val password: String
)

data class InValidLoginData(
    val email: String,
    val password: String
)

data class ChangeStatus(
    val newStatus: String
)

data class Chat(
    val chatTitle: String,
    val chatUser: String,
    val chatTime: String
)

data class User(
    val name: String,
    val status: String
)

data class SnackBarError(
    val wrongEmail:String,
    val wrongPassword:String
)