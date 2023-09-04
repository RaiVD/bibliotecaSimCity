package view.librarianView

import model.InputUserModel
import service.TableUserService
import view.MenuView

class UserEditView {
    private val inputUserModel = InputUserModel()
    private val tableUserService = TableUserService()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> MenuView()
                1 -> listUser()
                2 -> deleteUser()
                3 -> listUserId()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun menu() {
        println(
            "\n0. Menu Principal |" +
                    " 1. Lista de usuarios |" +
                    " 3. Deletar usuario |" +
                    " 4. Buscar usuario por ID"
        )
    }
    private fun listUser() {
        tableUserService.listUsers()
    }
    private fun deleteUser(){
        val id = inputUserModel.readIntFromUser("Qual o ID do Usuario: ")
        tableUserService.deleteUser(id)
    }
    private fun listUserId(){
        val id = inputUserModel.readIntFromUser("Qual o ID do Usuario: ")
        tableUserService.listSpecificUser(id)
    }
}