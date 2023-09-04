package view

import model.InputUserModel
import service.TableUserService
import view.librarianView.LibrarianView

class MenuView {
    private val inputUserModel = InputUserModel()
    fun start() {
        println("\n========================== Biblioteca SimCity ============================")
        var option: Int
        do {
            printMenu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> println("Encerrando o programa...")
                1 -> UserView().startOption()
                2 -> addUser()
                3 -> LibrarianView().startOption()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun addUser(){
        val name = inputUserModel.readStringFromUser("Qual seu nome: ")
        val cpf = inputUserModel.readStringFromUser("Digite seu CPF: ")
        val email = inputUserModel.readStringFromUser("Qual seu e-mail: ")

        TableUserService().addUser(cpf,name,email)
    }

    private fun printMenu() {
        println("0. Sair | 1. Login Usuario | 2. Cadastrar Usuario | 3. Login Bibliotecario")
    }
}