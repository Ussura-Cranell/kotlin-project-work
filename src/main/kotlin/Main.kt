fun main() {
    println("Hello world!\nВход в систему...\n")

    print("Введите имя пользователя: "); val userName = readln()
    print("Введите пароль от учетной записи: "); val userShadow = readln()

    if (userName == "Admin" && userShadow == "Password")
        println("Успешный вход в систему: $userName, добро пожаловать!")
    else println("Ошибка входа в систему: Неверное имя или пароль.")
}