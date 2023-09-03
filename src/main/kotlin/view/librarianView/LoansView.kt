package view.librarianView

import model.InputUserModel
import service.tableLoanBookService.TableLoanBookService
import view.MenuView

class LoansView {

    private val inputUserModel = InputUserModel()
    private val tableLoanBookService = TableLoanBookService()
    fun startOption() {
        var option: Int
        do {
            menu()
            option = inputUserModel.readIntFromUser("Qual opção você deseja: ")

            when (option) {
                0 -> MenuView()
                1 -> listLoans()
                2 -> updateDateLoans()
                3 -> loansID()
                else -> println("Opção inválida, tente novamente!")
            }
        } while (option != 0)
    }
    private fun menu() {
        println(
            "\n0. Menu Principal |" +
                    " 1. Emprestimos Ativos |" +
                    " 2. Atualizar Emprestimo |" +
                    " 3. Buscar emprestimo por ID"
        )
    }
    private fun listLoans() {
        tableLoanBookService.listLoanBooks()
    }
    private fun updateDateLoans() {
        val id = inputUserModel.readIntFromUser("Qual o ID do emprestimo: ")
        val date = inputUserModel.readStringFromUser("Qual a nova data de devolução (DD-MM-YYYY): ")
        tableLoanBookService.updateLoanBook(id, date)
    }
    private fun loansID(){
        val id = inputUserModel.readIntFromUser("Qual o ID do emprestimo: ")
        tableLoanBookService.listSpecificLoanBook(id)
    }

}