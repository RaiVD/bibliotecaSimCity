package view

import model.InputUserModel
import model.ValidDataBaseModel.Companion.isValidUserCredentials
import service.GetDataBaseService
import service.tableBookService.TableBookService
import service.tableLoanBookService.TableLoanBookService
import service.tableUserService.TableUserService

class LibrarianView {
    private val inputUserModel = InputUserModel()
    private val getDataBaseService = GetDataBaseService()
    private val tableBookService = TableBookService()
    private val tableLoanBookService = TableLoanBookService()
    private val tableUserService = TableUserService()
    fun startOption() {
        val name = inputUserModel.readStringFromUser("Digite seu nome: ")
        val password = inputUserModel.readStringFromUser("Digite seu CPF: ")

        if (isValidUserCredentials(name, password)) {
            println("========== Bem-Vindo $name ==========")
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
}