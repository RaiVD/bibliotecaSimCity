package service

import connection.Connect
import service.GetDataBaseService
import model.ValidDataBaseModel
import model.ValidDataBaseModel.Companion.isBookAlreadyLoaned
import model.ValidDataBaseModel.Companion.isValidBookId
import model.ValidDataBaseModel.Companion.isValidLoanId
import model.ValidDataBaseModel.Companion.isValidUserId
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class TableLoanBookService() {

    var connection = Connect().creatConnect()
    private val getDataBaseService = GetDataBaseService()

    fun addLoanBook(id_user: Int, id_book: Int, return_date: String) {
        try {
            if (!isValidUserId(id_user)) {
                println("ID de usuário inválido!")
                return
            }
            if (!isValidBookId(id_book)) {
                println("ID de livro inválido!")
                return
            }
            if (isBookAlreadyLoaned(id_book)) {
                println("Este livro já está emprestado!")
                return
            }

            val sql =
                "INSERT INTO loan_book (id_user, id_book, return_date) VALUES ($id_user, $id_book, ${parseDate(return_date)})"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Livro emprestado com sucesso!")
        } catch (e: DateTimeParseException) {
            println("Formato de data inválido. Use o formato DD-MM-AAAA.")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteLoanBook(id: Int) {
        if (!isValidLoanId(id)) {
            println("ID de empréstimo inválido!")
            return
        }
        val sql = "DELETE FROM loan_book WHERE id = $id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Empréstimo deletado com sucesso!")

            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateLoanBook(id: Int, return_date: String) {
        try {
            if (!isValidLoanId(id)) {
                println("ID de empréstimo inválido!")
                return
            }
            val sql =
                "UPDATE loan_book SET return_date = ${parseDate(return_date)} WHERE id = $id"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Empréstimo atualizado com sucesso!")
            statement.close()
        } catch (e: DateTimeParseException) {
            println("Formato de data inválido. Use o formato DD-MM-AAAA.")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listLoanBooks() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id, id_user, id_book, return_date FROM loan_book")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val id_user = resultSet.getInt("id_user")
                val id_book= resultSet.getInt("id_book")
                val returnDate = resultSet.getString("return_date")

                val userName = getDataBaseService.getUserName(id_user)
                val bookName = getDataBaseService.getBookName(id_book)

                println("ID do emprestimo: $id | ID Usuario: $id_user | Nome do Usuário: $userName | ID do livro: $id_book | Nome do Livro: $bookName | Data de Devolução: $returnDate")
            }

            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listSpecificLoanBook(idLoan: Int) {
        if (!isValidLoanId(idLoan)) {
            println("ID de empréstimo inválido!")
            return
        }

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM loan_book WHERE id=$idLoan")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val id_user = resultSet.getInt("id_user")
                val id_book= resultSet.getInt("id_book")
                val returnDate = resultSet.getString("return_date")

                val userName = getDataBaseService.getUserName(id_user)
                val bookName = getDataBaseService.getBookName(id_book)

                println("ID do emprestimo: $id | ID Usuario: $id_user | Nome do Usuário: $userName | ID do livro: $id_book | Nome do Livro: $bookName | Data de Devolução: $returnDate")
            }

            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listMyLoans(userId: Int) {
        // Verifique se o ID do usuário é válido antes de prosseguir
        if (!isValidUserId(userId)) {
            println("ID de usuário inválido!")
            return
        }

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM loan_book WHERE id_user = $userId")

        try {
            if (resultSet.isBeforeFirst) {
                println("Empréstimos feitos por você:")
                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val id_book = resultSet.getInt("id_book")
                    val returnDate = resultSet.getString("return_date")

                    val bookName = getDataBaseService.getBookName(id_book)

                    println("ID do empréstimo: $id | ID do livro: $id_book | Nome do Livro: $bookName | Data de Devolução: $returnDate")
                }
            } else {
                println("Você não possui empréstimos ativos.")
            }

            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
    private fun parseDate(dateStr: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDate.parse(dateStr, formatter)
    }
}
