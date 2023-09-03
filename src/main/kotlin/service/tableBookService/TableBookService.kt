package service.tableBookService

import connection.Connect
import service.GetDataBaseService
import model.ValidDataBaseModel
import model.ValidDataBaseModel.Companion.isValidAuthorId
import model.ValidDataBaseModel.Companion.isValidBookId
import model.ValidDataBaseModel.Companion.isValidBookInfo
import java.sql.SQLException

class TableBookService {

    private val connection = Connect().creatConnect()
    private val getDataBaseService = GetDataBaseService()

    fun addBook(isbn: String, name_book: String, id_author: Int) {
        try {
            if (!isValidBookInfo(isbn, name_book)) {
                println("As informações do livro não podem estar vazias ou nulas.")
                return
            }
            val sql =
                "INSERT INTO book (isbn, name_book, id_author) VALUES ('$isbn', '$name_book', '$id_author')"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Livro $name_book adicionado com sucesso!")

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteBook(id: Int) {
        if (!isValidBookId(id)) {
            println("ID de livro inválido!")
            return
        }
        val sql =
            "DELETE FROM book WHERE id=$id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Livro deletado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateBook(id: Int, isbn: String, name_book: String, id_author: Int) {
        try {
            if (!isValidBookId(id)) {
                println("ID de livro inválido!")
                return
            }
            if (!isValidBookInfo(isbn, name_book)) {
                println("As informações do livro não podem estar vazias ou nulas.")
                return
            }
            if (!isValidAuthorId(id)) {
                println("ID de autor inválido!")
                return
            }
            val sql =
                "UPDATE book SET isbn='$isbn', name_book='$name_book', id_author='$id_author' WHERE id=$id"
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Livro $name_book atualizado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listBooks() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id, isbn, name_book, id_author FROM book")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val isbn = resultSet.getString("isbn")
                val name_book = resultSet.getString("name_book")
                val id_author = resultSet.getInt("id_author")

                val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | ISBN do livro: $isbn | Nome do livro: $name_book | Nome do autor: $authorName")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        println()
    }

    fun listSpecificBook(idBook: Int) {
        if (!isValidBookId(idBook)) {
            println("ID de livro inválido!")
            return
        }
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM book WHERE id=$idBook")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val isbn = resultSet.getString("isbn")
                val name_book = resultSet.getString("name_book")
                val id_author = resultSet.getInt("id_author")

                val authorName = getDataBaseService.getAuthorName(id_author)

                println("ID: $id | ISBN do livro: $isbn | Nome do livro: $name_book | Nome do autor: $authorName")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}
