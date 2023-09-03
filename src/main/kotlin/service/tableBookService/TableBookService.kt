package service

import connection.Connect
import java.sql.SQLException

class TableBookService {

    private val connection = Connect().creatConnect()
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
            } else if (!(isValidBookInfo(isbn, name_book) || id_author != null)) {
                println("As informações do livro não podem estar vazias ou nulas.")
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
                val id = resultSet.getInt("idBook")
                val isbn = resultSet.getString("isbn")
                val name_book = resultSet.getString("name_book")
                val id_author = resultSet.getInt("id_author")

                val authorName = getAuthorName(id_author)

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
                val id = resultSet.getInt("idBook")
                val isbn = resultSet.getString("isbn")
                val name_book = resultSet.getString("name_book")
                val id_author = resultSet.getInt("id_author")

                val authorName = getAuthorName(id_author)

                println("ID: $id | ISBN do livro: $isbn | Nome do livro: $name_book | Nome do autor: $authorName")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun isValidBookId(id: Int): Boolean {
        val sql = "SELECT COUNT(*) FROM book WHERE id=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setInt(1, id)
            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count = resultSet.getInt(1)

            resultSet.close()
            preparedStatement.close()

            return count > 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }

    private fun isValidBookInfo(nameBook: String, author: String): Boolean {
        return nameBook.isNotBlank() && author.isNotBlank()
    }
    private fun getAuthorName(authorId: Int): String {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT name_author FROM author WHERE id = $authorId")

        return if (resultSet.next()) {
            resultSet.getString("name_author")
        } else {
            "Autor não encontrado"
        }
    }
}
