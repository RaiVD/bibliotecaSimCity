package service.tableAuthorService

import connection.Connect
import model.ValidDataBaseModel.Companion.isValidAuthorId
import model.ValidDataBaseModel.Companion.isValidAuthorInfo
import java.sql.Connection
import java.sql.SQLException

class TableAuthorService {

    private val connection: Connection

    // Configura a conexão no construtor
    init {
        connection = Connect().creatConnect()
    }

    fun addAuthor(name_author: String) {
        try {
            if (!isValidAuthorInfo(name_author)) {
                println("O nome do autor não pode estar vazio ou nulo.")
                return
            }
            val sql = "INSERT INTO author (name_author) VALUES ('$name_author')"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Autor $name_author adicionado com sucesso!")

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteAuthor(id: Int) {
        if (!isValidAuthorId(id)) {
            println("ID de autor inválido!")
            return
        }
        val sql = "DELETE FROM author WHERE id=$id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Autor deletado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateAuthor(id: Int, name_author: String) {
        try {
            if (!isValidAuthorId(id) || !isValidAuthorInfo(name_author)) {
                println("ID de autor inválido ou nome do autor vazio ou nulo.")
                return
            }
            val sql = "UPDATE author SET name_author='$name_author' WHERE id=$id"
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Autor atualizado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listAuthors() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id, name_author FROM author")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name_author = resultSet.getString("name_author")

                println("ID: $id | Nome do autor: $name_author")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        println()
    }

    fun listSpecificAuthor(idAuthor: Int) {
        if (!isValidAuthorId(idAuthor)) {
            println("ID de autor inválido!")
            return
        }
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM author WHERE id=$idAuthor")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val name_author = resultSet.getString("name_author")

                println("ID: $id | Nome do autor: $name_author")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
