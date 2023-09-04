package view

import model.InputUserModel
import service.GetDataBaseService
import model.ValidDataBaseModel.Companion.isValidUserCredentials
import service.TableBookService
import service.TableLoanBookService
import service.TableUserService

class UserView {
    private val inputUserModel = InputUserModel()
    private val getDataBaseService = GetDataBaseService()
    private val tableBookService = TableBookService()
    private val tableLoanBookService = TableLoanBookService()
    private val tableUserService = TableUserService()

    private var loggedUnUserId: Int = -1
    fun startOption() {
        val name = inputUserModel.readStringFromUser("Digite seu nome: ")
        val password = inputUserModel.readStringFromUser("Digite seu CPF: ")

        if (isValidUserCredentials(name, password)) {
            loggedUnUserId = getDataBaseService.getUserIdByName(name)
            println("\n========================== Bem-Vindo $name ==========================")
            tableUserService.userInfoByAlias(name)
            var option: Int
            do {
                menu()
                option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

                when (option) {
                    0 -> MenuView()
                    1 -> listBook()
                    2 -> listMyLoans()
                    3 -> myLoans()
                    4 -> deleteLoans()
                    5 -> updateData()
                    else -> println("Opção inválida, tente novamente!")
                }
            } while (option != 0)
        } else {
            println("Senha ou nome invalidos!")
        }
    }
    private fun menu() {
        println("\n0. Menu Principal |" +
                " 1. Livros disponiveis |" +
                " 2. Meus Emprestimos |" +
                " 3. Realizar um novo emprestimo |" +
                " 4. Deletar um emprestimo |" +
                " 5. Atualizar dados"
        )
    }
    private fun listBook(){
        tableBookService.listBooks()
    }
    private fun listMyLoans() {
        tableLoanBookService.listMyLoans(loggedUnUserId)
    }
    private fun myLoans(){
        val idBook = inputUserModel.readIntFromUser("Qual o ID do livro: ")
        val dateReturn = inputUserModel.readStringFromUser("Qual a data de devolução (DD-MM-YYYY): ")

        tableLoanBookService.addLoanBook(loggedUnUserId,idBook,dateReturn)
    }
    private fun deleteLoans(){
        val id = inputUserModel.readIntFromUser("Qual o ID o emprestimo: ")
        tableLoanBookService.deleteLoanBook(id)
    }
    private fun updateData() {
        val id = inputUserModel.readIntFromUser("Qual o ID da sua conta: ")
        val email = inputUserModel.readStringFromUser("Qual o novo email: ")
        tableUserService.updateUser(id, email)
    }
}