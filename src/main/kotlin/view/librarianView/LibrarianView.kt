package view.librarianView

import model.InputUserModel
import model.ValidDataBaseModel.Companion.isValidLibrarianCredentials
import view.MenuView

class LibrarianView {
    private val inputUserModel = InputUserModel()

    fun startOption() {
        val name = inputUserModel.readStringFromUser("Digite seu nome: ")
        val password = inputUserModel.readStringFromUser("Digite seu CPF: ")

        if (isValidLibrarianCredentials(name, password)) {
            println("\n========================== Bem-Vindo $name ==========================")
            var option: Int
            do {
                menu()
                option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

                when (option) {
                    0 -> MenuView().start()
                    1 -> BookView().startOption()
                    2 -> LoansView().startOption()
                    3 -> AuthorView().startOption()
                    4 -> UserEditView().startOption()
                    else -> println("Opção inválida, tente novamente!")
                }
            } while (option != 0)
        } else {
            println("Senha ou nome invalidos!")
        }
    }
    private fun menu() {
        println("\n0. Menu Principal |" +
                " 1. Livros |" +
                " 2. Emprestimos |" +
                " 3. Autores |" +
                " 4. Usuarios"
        )
    }
}