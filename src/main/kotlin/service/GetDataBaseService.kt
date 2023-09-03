package service

import connection.Connect
import java.sql.SQLException

class GetDataBaseService {
    private val connection = Connect().creatConnect()

    // Validar o nome de cada ID
    fun getUserName(id: Int): String {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT alias FROM users WHERE id = $id")

        return if (resultSet.next()) {
            resultSet.getString("alias")
        } else {
            "Usuario não encontrado"
        }
    }
    fun getBookName(id: Int): String {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT name_book FROM book WHERE id = $id")

        return if (resultSet.next()) {
            resultSet.getString("name_book")
        } else {
            "Livro não encontrado"
        }
    }
    fun getAuthorName(authorId: Int): String {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT name_author FROM author WHERE id = $authorId")

        return if (resultSet.next()) {
            resultSet.getString("name_author")
        } else {
            "Autor não encontrado"
        }
    }

    // Validar o ID do usuario logado
    fun getUserIdByName(name: String): Int {
        val sql = "SELECT id FROM Users WHERE alias = ?"
        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, name)
            val resultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                return resultSet.getInt("id")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return -1 // Retorna um valor inválido caso o usuário não seja encontrado
    }
}