package view.librarianView

import model.InputUserModel
import service.tableBookService.TableBookService
import view.MenuView

class BookView {

    private val inputUserModel = InputUserModel()
    private val tableBookService = TableBookService()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> MenuView()
                1 -> listBook()
                2 -> addBook()
                3 -> deleteBook()
                4 -> listBookId()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun menu() {
        println("\n0. Menu Principal |" +
                    " 1. Lista de livros |" +
                    " 2. Adicionar livro |" +
                    " 3. Deletar livro |" +
                    " 4. Buscar livro por ID"
        )
    }
    private fun listBook() {
        tableBookService.listBooks()
    }
    private fun addBook(){
        val isbn = inputUserModel.readStringFromUser("Qual o ISBN do livro: ")
        val name = inputUserModel.readStringFromUser("Qual o nome do livro: ")
        val author = inputUserModel.readIntFromUser("Qual o ID do autor: ")

        tableBookService.addBook(isbn, name, author)
    }
    private fun deleteBook(){
        val id = inputUserModel.readIntFromUser("Qual o ID do livro: ")
        tableBookService.deleteBook(id)
    }
    private fun listBookId(){
        val id = inputUserModel.readIntFromUser("Qual o ID do livro: ")
        tableBookService.listSpecificBook(id)
    }
}