package view.librarianView

import model.InputUserModel
import service.tableAuthorService.TableAuthorService
import view.MenuView

class AuthorView {
    private val inputUserModel = InputUserModel()
    private val tableAuthorService = TableAuthorService()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> MenuView()
                1 -> listAuthor()
                2 -> addAuthor()
                3 -> deleteAuthor()
                4 -> listAuthorId()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }

    private fun menu() {
        println(
            "\n0. Menu Principal |" +
                    " 1. Listar autores |" +
                    " 2. Adicionar autor |" +
                    " 3. Deletar autor |" +
                    " 4. Buscar autor por ID"
        )
    }
    private fun listAuthor(){
        tableAuthorService.listAuthors()
    }
    private fun addAuthor(){
        val name = inputUserModel.readStringFromUser("Digite o nome do autor: ")
        tableAuthorService.addAuthor(name)
    }
    private fun deleteAuthor(){
        val id = inputUserModel.readIntFromUser("Qual o ID do autor: ")
        tableAuthorService.deleteAuthor(id)
    }
    private fun listAuthorId(){
        val id = inputUserModel.readIntFromUser("Qual o ID do autor: ")
        tableAuthorService.listSpecificAuthor(id)
    }
}