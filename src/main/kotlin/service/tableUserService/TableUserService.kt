package service.tableUserService

import connection.Connect
import model.ValidDataBaseModel
import model.ValidDataBaseModel.Companion.isValidEmail
import model.ValidDataBaseModel.Companion.isValidUserId
import model.ValidDataBaseModel.Companion.isValidUserInfo
import java.sql.SQLException

class TableUserService {

    var connection = Connect().creatConnect()

    fun addUser(cpf: String, alias: String, email: String) {
        try {
            if (!isValidUserInfo(cpf, alias, email)) {
                println("As informações do usuário não podem estar vazias ou nulas.")
                return
            }
            if (!isValidEmail(email)){
                println("Email invalido!")
                return
            }
            val sql =
                "INSERT INTO users (cpf, alias, email) VALUES ('$cpf', '$alias', '$email')"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Usuário $alias adicionado com sucesso!")

        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun deleteUser(id: Int) {
        if (!isValidUserId(id)) {
            println("ID de usuário inválido!")
            return
        }
        val sql =
            "DELETE FROM users WHERE id=$id"

        try {
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Usuário deletado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun updateUser(id: Int, email: String) {
        try {
            if (!isValidUserId(id)) {
                println("ID de usuário inválido!")
                return
            }
            if (!isValidEmail(email) || email.isNotBlank()){
                println("Email invalido!")
                return
            }
            val sql =
                "UPDATE users SET email='$email' WHERE id=$id"

            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            println("Usuário atualizado com sucesso!")
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun listUsers() {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT id, cpf, alias, email FROM users")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val cpf = resultSet.getString("cpf")
                val alias = resultSet.getString("alias")
                val email = resultSet.getString("email")

                println("ID: $id | CPF: $cpf | Alias: $alias | Email: $email")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        println()
    }

    fun listSpecificUser(idUser: Int) {
        if (!isValidUserId(idUser)) {
            println("ID de usuário inválido!")
            return
        }
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users WHERE id=$idUser")

        try {
            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val cpf = resultSet.getString("cpf")
                val alias = resultSet.getString("alias")
                val email = resultSet.getString("email")

                println("ID: $id | CPF: $cpf | Alias: $alias | Email: $email")
            }
            resultSet.close()
            statement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun userInfoByAlias(alias: String) {
        val sql = "SELECT id, cpf, email FROM users WHERE alias=?"

        try {
            val preparedStatement = connection.prepareStatement(sql)
            preparedStatement.setString(1, alias)
            val resultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                val id = resultSet.getInt("id")
                val cpf = resultSet.getString("cpf")
                val email = resultSet.getString("email")
                println("Informações da Conta:\n ID: $id | CPF: $cpf | Nome: $alias | Email: $email")
            } else {
                println("Usuário com o nome $alias não encontrado.")
            }

            resultSet.close()
            preparedStatement.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
